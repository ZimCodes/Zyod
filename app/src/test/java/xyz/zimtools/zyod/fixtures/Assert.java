package xyz.zimtools.zyod.fixtures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Assert {
    public static void boolTrue(boolean value, String optionName) {
        optionName = intoOpt(optionName);
        assertTrue(value, String.format("'--%s' option should be present.", optionName));
    }

    public static void eqStr(String value, String answer) {
        assertEquals(value, answer);
    }

    private static String intoOpt(String optionName) {
        return optionName.replace(" ", "-");
    }
}