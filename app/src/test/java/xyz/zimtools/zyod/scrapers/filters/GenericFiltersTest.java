package xyz.zimtools.zyod.scrapers.filters;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Tests Generic filters for Scrapers
 */
class GenericFiltersTest {
    private static ScrapeFilter filter;
    private final Pattern queryPat = Pattern.compile(GenericScrapeFilter.QUERY_REGEX);

    @BeforeAll
    static void init() {
        filter = new GenericScrapeFilter();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "https://api.example.com/search?q=title:DNA",
            "https://api.plos.org/search?q=title:%22Drosophila%22%20and%20body:%22RNA%22&fl=id," +
                    "abstract",
            "http://domain.tld/function.cgi?url=http://example" +
                    ".com/&name=Fonzi&mood=happy&coat=leather",
            "https://example.com/post?url=http://domain.tld/&title=The title of a post",
            "http://example.com/post?url=http://domain.tld/&title=The%20title%20of%20a%20post",
            "https://www.example.com:80/services/search?q=1&456+true&will",
            "/ccadmin/v1/products?fields=items.id,items.displayName",
            "/ccadmin/v1/products?q=orderLimit lt 10"
    })
    void removeQuery(String phrase) {
        String newPhrase = filter.apply(phrase);
        assertFalse(queryPat.matcher(newPhrase).find(), String.format("Query was not " +
                "removed from %s. Result: %s", phrase, newPhrase));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Quotations should be within double quotes ( \" like this \" )",
            "He said that he \"hoped I would be there.\"",
            "\"Why,\" I asked, \"don't you care?\"",
            "Seasons are changing with the flow of time."
    })
    void removeDoubleQuotes(String phrase) {
        String newPhrase = filter.apply(phrase);
        assertFalse(newPhrase.contains("\""), String.format("Double Quotation was not removed " +
                "from %s. Result: %s", phrase, newPhrase));
    }
}