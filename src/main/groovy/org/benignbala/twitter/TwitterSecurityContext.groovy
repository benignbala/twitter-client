package org.benignbala.twitter

import groovy.transform.TupleConstructor

/**
 * Created by bala on 5/12/15.
 */
@TupleConstructor
class TwitterSecurityContext {
    String consumerKey
    String consumerSecret

    String getBasicAuthToken() {
        def bConsumerKey = consumerKey
        def bConsumerSecret = consumerSecret
        println "Will encode: ${bConsumerKey}:${bConsumerSecret}"
        return "${bConsumerKey}:${bConsumerSecret}".bytes.encodeBase64().toString()
    }
}
