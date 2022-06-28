package xyz.zimtools.zyod.fixtures.asserts;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class ScraperAssert {
    public static void resourceCount(int expected, int got, String resourceName, String url) {
        assertEquals(expected, got, String.format("At %s, scraper is expected to retrieve %d %s, " +
                "but got %d instead.", url, expected, resourceName, got));
    }
}