package xyz.zimtools.zyod.fixtures.asserts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BrowserAssert {

    public static void settingTrue(boolean value, String settingName) {
        assertTrue(value, String.format("The %s setting was not added to the browser!", settingName));
    }

    public static void settingEquals(Object value, Object correct, String settingName) {
        assertEquals(correct, value, String.format("The %s setting was not applied to the web " +
                "driver.", settingName));
    }
}