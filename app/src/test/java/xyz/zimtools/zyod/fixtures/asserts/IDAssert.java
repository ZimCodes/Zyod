package xyz.zimtools.zyod.fixtures.asserts;

import xyz.zimtools.zyod.od.ODType;
import xyz.zimtools.zyod.od.identifiers.ODIdentifier;

import static org.junit.jupiter.api.Assertions.assertTrue;

public final class IDAssert {
    public static void isOD(ODIdentifier identifier, ODType odType) {
        assertTrue(
                identifier.isOD(),
                String.format("the %s identifier was not able to identify the OD correctly.",
                        odType));
    }
}