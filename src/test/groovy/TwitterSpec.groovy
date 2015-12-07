/**
 * Created by balacha1 on 12/5/2015.
 */
import spock.lang.Specification
import org.benignbala.twitter.Twitter

class TwitterSpec extends Specification {
    def "fetch all tweets"() {
        when:
        def twitter = new Twitter("uCcjoM0scKOudKPCnuT9HtWra", "OGaIOA5lAg0KQcURah9XpMn1IboDSctIWKqjsnFlbL7OPX9pi1")

        then:
        assert twitter != null
    }
}
