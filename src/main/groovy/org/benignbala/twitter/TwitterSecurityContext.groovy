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
        def bConsumerKey = consumerKey.bytes.encodeBase64().toString()
        def bConsumerSecret = consumerSecret.bytes.encodeBase64().toString()
        return "${bConsumerKey}:${bConsumerSecret}".bytes.encodeBase64().toString()
    }
}
