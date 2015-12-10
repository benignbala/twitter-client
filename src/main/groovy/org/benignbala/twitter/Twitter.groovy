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

    String fetchFavourites(String screenName) {
        def securityContext = new TwitterSecurityContext(consumerKey, consumerSecret)
        def api = new TwitterApi(securityContext)
        def tweetsFile = api.getFavouriteTweets(screenName)
        return tweetsFile
    }
}

