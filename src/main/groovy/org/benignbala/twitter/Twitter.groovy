package org.benignbala.twitter

import groovy.transform.TupleConstructor

import java.text.SimpleDateFormat

/**
 * Created by bala on 5/12/15.
 */
@TupleConstructor
class Twitter {
    String consumerKey
    String consumerSecret

    String fetchAllTweets(String screenName ) {
        def securityContext = new TwitterSecurityContext(consumerKey, consumerSecret)
        def api = new TwitterApi(securityContext)
        def tweetsFile = api.getAllTweets(screenName)
        return tweetsFile
    }
}

class Client {
    static void main(String... args) {
        def cKey = "hturdxkVMLGAnOCwe8VLA"
        def cSecret = "AlcAwZT0hnlB4vEcbb4eQ4Yaf1p2PVYzTQYBvH13Q"

        def aKey = "15194421-brBeFUqUbslsKnAV9N4nNHvoKNAFbduO3I24J8WKC"
        def aSecret = "nvtJFNhmsiwSOdRQMVxdu8xSlstW0vw69dfz8gDbJhYfq"

        def twitter = new Twitter(cKey, cSecret, aKey, aSecret)
        def file = twitter.fetchAllTweets("benignbala")
        println file


    }
}
