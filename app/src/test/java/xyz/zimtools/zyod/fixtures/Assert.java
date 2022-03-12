package xyz.zimtools.zyod.fixtures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Assert {
    public static void argTrue(boolean value, String optionName) {
        optionName = intoOpt(optionName);
        assertTrue(value, String.format("'--%s' option should be present.", optionName));
    }

    public static void settingTrue(boolean value, String settingName) {
        assertTrue(value, String.format("The %s setting was not added to the browser!", settingName));
    }

    public static void settingEquals(Object value, Object correct, String settingName) {
        assertEquals(correct, value, String.format("The %s setting was not applied to the web " +
                "driver.", settingName));
    }

    public static void sameDriver(String value, String correctBrowser) {
        assertEquals(correctBrowser, value, "Wrong web driver have been selected!");
    }

    public static void isPositive(int value, String optionName) {
        assertTrue(value > 0, String.format("'--%s' should be a " +
                "positive number, instead it is %d", optionName, value));
    }

    private static String intoOpt(String optionName) {
        return optionName.replace(" ", "-");
    }
}