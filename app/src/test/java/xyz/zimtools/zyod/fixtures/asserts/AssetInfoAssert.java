package xyz.zimtools.zyod.fixtures.asserts;

import xyz.zimtools.zyod.assets.info.ODParser;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AssetInfoAssert {
    public static <T> void infoExists(String od, String navType, ODParser<T> parser) {
        T info = parser.getInfo(od, navType);
        assertNotNull(info, String.format("OD type '%s' with navigation type '%s' does not " +
                "exist!", od, navType));
    }

    public static <T> void infoNotExists(String od, String navType, ODParser<T> parser) {
        T info = parser.getInfo(od, navType);
        assertNull(info, String.format("OD type '%s' with navigation type '%s' does not " +
                "exist!", od, navType));
    }
}