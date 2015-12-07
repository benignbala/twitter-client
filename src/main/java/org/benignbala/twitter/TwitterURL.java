package org.benignbala.twitter;
/**
 * Created by bala on 5/12/15.
 */
public class TwitterURL {
    public final static String urlBase = "https://api.twitter.com/1.1/";
    public final static String statusUrl = urlBase + "statuses/";
    public final static String bearerUrl = urlBase + "oauth2/token";
    static String getStatusUrl() {
        return statusUrl;
    }
    static String getOAuthUrl() { return bearerUrl; }

}
