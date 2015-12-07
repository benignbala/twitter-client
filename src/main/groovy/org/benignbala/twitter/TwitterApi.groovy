package org.benignbala.twitter

import groovy.transform.TupleConstructor
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.RESTClient

/**
 * Created by bala on 5/12/15.
 */

@TupleConstructor
class TwitterApi {
    TwitterSecurityContext securityContext
    RESTClient restClient

    String getAllTweets(String screenName)  {
        File tweetsFile = new File("/tmp/tweets.json")
        restClient = new RESTClient(org.benignbala.twitter.TwitterURL.getStatusUrl())
        def bearerToken = securityContext.getBearerToken()
        def resp = restClient.get (path: 'user_timeline', query: [screen_name: screenName])
        println resp.data
        tweetsFile << resp.data
    }

    private String getBearerToken() {
        def basic = securityContext.getBasicAuthToken()
        def httpBuilder = new HTTPBuilder(TwitterURL.getOAuthUrl())
        httpBuilder.auth.basic(securityContext.getConsumerKey(), securityContext.getConsumerSecret())
        def resp = httpBuilder.post(grant_type: "client_credentials")
        println resp.properties
    }

}
