package xyz.zimtools.zyod.fixtures.asserts;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArgAssert {

    public static void argTrue(boolean value, String optionName) {
        optionName = intoOpt(optionName);
        assertTrue(value, String.format("'--%s' option should be present.", optionName));
    }

    public static void isPositive(long value, String optionName) {
        assertTrue(value > 0, String.format("'--%s' should be a " +
                "positive number, instead it is %d", optionName, value));
    }

    private static String intoOpt(String optionName) {
        return optionName.replace(" ", "-");
    }
}