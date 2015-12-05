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
        
    }
}
