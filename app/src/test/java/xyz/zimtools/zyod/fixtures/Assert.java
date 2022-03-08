package xyz.zimtools.zyod.fixtures;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Assert {
    public static void boolTrue(boolean value, String optionName) {
        optionName = intoOpt(optionName);
        assertTrue(value, String.format("'--%s' option should be present.", optionName));
    }

    public static void isPositive(int value, String optionName) {
        assertTrue(value > 0, String.format("'--%s' should be a " +
                "positive number, instead it is %d", optionName, value));
    }

    private static String intoOpt(String optionName) {
        return optionName.replace(" ", "-");
    }
}