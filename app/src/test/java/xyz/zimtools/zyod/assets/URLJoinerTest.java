package xyz.zimtools.zyod.assets;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static xyz.zimtools.zyod.fixtures.URLDefault.BASE_URL;
import static xyz.zimtools.zyod.fixtures.URLDefault.REL_URL;

class URLJoinerTest {

    @Test
    void urlJoiner() {
        String newURL = BASE_URL + REL_URL;
        assertEquals(newURL, ODUrl.joiner(BASE_URL, newURL).toString(), "Two urls with same base did not " +
                "join correctly.");

        assertEquals(newURL, ODUrl.joiner(BASE_URL, REL_URL).toString(), "An absolute url & relative " +
                "url was not joined correctly.");
    }

    /**
     * Tests if {@link xyz.zimtools.zyod.assets.ODUrl#joiner(String, String)} can join base url
     * containing queries.
     */
    @Test
    @DisplayName("Query URL Joiner")
    void queryJoiner() {
        String url = "https://www.example.com/CoolFiles/index.php";
        String query = "?dir=AMC1260+XMT34635%2F";
        String rel = "/kz/CoolFiles/index.php" + query;
        String newURL = "https://www.example.com/kz/CoolFiles/index.php" + query;

        assertEquals(newURL, ODUrl.joiner(url, newURL).toString(), "Two query urls with the same base " +
                "did not join correctly.");
        assertEquals(newURL, ODUrl.joiner(url, rel).toString(), "An absolute query url & relative " +
                "url was not joined correctly.");
    }

    /**
     * Tests if {@link xyz.zimtools.zyod.assets.ODUrl#joiner(String, String)} can join urls with
     * {@code /?/} as a path.
     */
    @Test
    void queryPathJoiner() {
        String queryPathURL = BASE_URL + "/?";
        String newURL = queryPathURL + REL_URL;

        assertEquals(newURL, ODUrl.joiner(queryPathURL, newURL).toString(), "Two query path urls with the" +
                " " +
                "same base did not join correctly.");
        assertEquals(newURL, ODUrl.joiner(queryPathURL, REL_URL).toString(), "An absolute query path url " +
                "& " +
                "relative url was not joined correctly.");
    }

    /**
     * Tests if {@link xyz.zimtools.zyod.assets.ODUrl#joiner(String, String)} can join base url
     * containing {@code /#/} as a path.
     */
    @Test
    void fragmentPathJoiner() {
        String fragmentPathURL = BASE_URL + "/#";
        String newURL = fragmentPathURL + REL_URL;

        assertEquals(newURL, ODUrl.joiner(fragmentPathURL, newURL).toString(), "Two fragment path urls " +
                "with " +
                "the same base did not join correctly.");
        assertEquals(newURL, ODUrl.joiner(fragmentPathURL, REL_URL).toString(), "An absolute fragment " +
                "path & " +
                "relative url was not joined correctly.");
    }
}