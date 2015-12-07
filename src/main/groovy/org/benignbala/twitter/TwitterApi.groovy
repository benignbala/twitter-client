package org.benignbala.twitter

import groovy.transform.TupleConstructor
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient

/**
 * Created by bala on 5/12/15.
 */


class TwitterApi {
    TwitterSecurityContext securityContext
    HTTPBuilder restClient

    TwitterApi(TwitterSecurityContext securityContext) {
        this.securityContext = securityContext
    }

    String getAllTweets(String screenName)  {
        File tweetsFile = new File("/tmp/tweets.json")
        restClient = new HTTPBuilder(org.benignbala.twitter.TwitterURL.getStatusUrl())
        restClient.handler.failure = {resp, json ->
            println json
        }
        def bearerToken = getBearerToken()
        restClient.setHeaders("Authorization": "Bearer ${bearerToken}")
        restClient.get (path: 'user_timeline.json', query: [screen_name: screenName]) { resp, json ->
            json.each {
                println it.text
                tweetsFile << it.text << "\n"
            }

        }

        //tweetsFile << resp.data
        return ""
    }

    private String getBearerToken() {
        def basic = securityContext.getBasicAuthToken()
        String bearer
        def httpBuilder = new HTTPBuilder(TwitterURL.getOAuthUrl())
        httpBuilder.handler.failure = {resp, json ->
            println json
        }
        httpBuilder.setHeaders("Authorization": "Basic $basic")
        println httpBuilder.uri
        println httpBuilder.headers
        try {
            httpBuilder.post(body: [grant_type: "client_credentials"]) { resp, json ->
                println json.access_token
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
