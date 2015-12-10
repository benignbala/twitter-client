package org.benignbala.twitter

import groovyx.net.http.HTTPBuilder
import groovyx.net.http.HttpResponseException

/**
 * Created by bala on 5/12/15.
 */


class TwitterApi {
    TwitterSecurityContext securityContext
    HTTPBuilder restClient

    TwitterApi(TwitterSecurityContext securityContext) {
        this.securityContext = securityContext
    }

    String getAllTweets(String screenName) {
        File tweetsFile = new File("/tmp/tweets.json")
        restClient = new HTTPBuilder(org.benignbala.twitter.TwitterURL.getStatusUrl())
        def bearerToken = getBearerToken()
        restClient.handler.failure = { resp, json ->
            println resp.statusLine
            println json
            bearerToken = getBearerToken()
            if (resp.status == 429) {
                println (resp.headers.'X-Rate-Limit-Reset')
                System.exit(1)
            }
        }
        def since = 1
        def tweets = []
        def times = 1
        while (true) {
            def count = 0
            // println "Going at it again...: ${count}"
            restClient.setHeaders("Authorization": "Bearer ${bearerToken}")
            def qMap = [screen_name: screenName, count: 200, since_id: 1]
            if (times > 1) {
                qMap.remove('since_id')
                qMap['max_id'] = since
            }
            restClient.get(path: 'user_timeline.json', query: qMap) { resp, json ->
                json.each {
                    tweets << "${it.created_at}: ${it.text}"
                    if (count == 0) {
                        since = it.id
                    } else {
                        since = it.id <= since ? it.id : since
                    }
                    count++
                }
                // println "Count: ${count}\n"

            }
            // println "Done this time: ${times}"
            times++
            if (count == 1 || times >= 290) {
                break;
            }
        }
        // print tweets
        tweets.each {
           tweetsFile << "${it}\n"
        }
        return tweetsFile.absolutePath
    }

    String getFavouriteTweets(String screenName) {
        File tweetsFile = new File("/tmp/favtweets.json")
        restClient = new HTTPBuilder(org.benignbala.twitter.TwitterURL.getFavUrl())
        def bearerToken = getBearerToken()
        restClient.handler.failure = { resp, json ->
            bearerToken = getBearerToken()
        }
        def since = 0
        def tweets = []
        restClient.setHeaders("Authorization": "Bearer ${bearerToken}")
        restClient.get(path: 'list.json', query: [screen_name: screenName]) { resp, json ->
            json.each {
                tweets << it.text
                tweetsFile << it.text << "\n"
            }

        }
        // print tweets
        return tweetsFile.absolutePath
    }

    private String getBearerToken() {
        def basic = securityContext.getBasicAuthToken()
        String bearer
        def httpBuilder = new HTTPBuilder(TwitterURL.getOAuthUrl())
        httpBuilder.handler.failure = { resp, json ->
            println json
        }
        httpBuilder.setHeaders("Authorization": "Basic $basic")
        // println httpBuilder.uri
        // println httpBuilder.headers
        try {
            httpBuilder.post(body: [grant_type: "client_credentials"]) { resp, json ->
                // println json.access_token
                bearer = json.access_token
            }
            return bearer
        }
        catch (HttpResponseException e) {
            println e.message
            println e.getResponse().data
        }
    }

}
