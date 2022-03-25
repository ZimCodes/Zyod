package xyz.zimtools.zyod.scrapers.filters;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests scrape filters for OneDriveVercelIndex
 */
class OneDriveVercelIndexFilterTest {
    private static final int NEW_LINE_CHAR_CODE = 10;
    private static ScrapeFilter filter;

    @BeforeAll
    static void init() {
        filter = new OneDriveVercelIndexScrapeFilter();
    }

    @ParameterizedTest
    @ValueSource(strings = {"The antelope sprinted across the pavement.\nIt did not look both " +
            "ways before crossing.",
            "Cat\nDog\nOstrich\nGiraffe are all animals.\n",
            "Iapetus's north pole is not visible here, nor is any part of the bright trailing hemisphere."
    })
    void newLineToSpace(String phrase) {
        long initialCount = phrase.chars().filter(charCode -> charCode == NEW_LINE_CHAR_CODE).count();
        long expected = initialCount != 0 ? initialCount - 1 : initialCount;
        String newPhrase = filter.apply(phrase);
        long count = newPhrase.chars().filter(charCode -> charCode == NEW_LINE_CHAR_CODE).count();
        assertEquals(expected, count, "The number of '\\n' should be reduced by one when " +
                "applying OneDriveVercelIndex scraper filter.");
    }
}