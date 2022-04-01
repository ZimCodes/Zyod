package xyz.zimtools.zyod.fixtures.asserts;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NavAssert {
    public static void resourceCount(int expected, int got, String navType,String resourceName,
                                     String url) {
        assertEquals(expected, got, String.format("At %s, %s is expected to retrieve %d %s, " +
                "but got %d instead.", url, navType, expected, resourceName, got));
    }
}