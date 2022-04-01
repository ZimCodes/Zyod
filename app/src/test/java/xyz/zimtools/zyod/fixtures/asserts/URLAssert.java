package xyz.zimtools.zyod.fixtures.asserts;

import xyz.zimtools.zyod.assets.ODUrl;

import static org.junit.jupiter.api.Assertions.*;

public class URLAssert {

    public static void popAssert(String poppedItem, ODUrl parsedURL) {
        assertEquals(poppedItem, parsedURL.popPath(), "The path that was removed is incorrect.");
    }
    /**
     * Asserts if URLs match.
     *
     * @param correctURL the expected URL
     * @param url        the URL ini question to compare with the correctURL param
     */
    public static void urlAssert(String correctURL, String url) {
        assertEquals(correctURL, url, "URLs do not match!");
    }

    public static void fileFalse(String url) {
        assertFalse(ODUrl.isFile(url), String.format("%s is not a URL pointing to a file!", url));
    }

    public static void fileTrue(String url) {
        assertTrue(ODUrl.isFile(url), String.format("%s is a URL pointing to a file!", url));
    }
}