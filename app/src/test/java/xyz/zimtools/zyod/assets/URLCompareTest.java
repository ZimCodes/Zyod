package xyz.zimtools.zyod.assets;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests if URLs are compared correctly
 * */
class URLCompareTest {
    @ParameterizedTest
    @MethodSource("getParams")
    void urlEquals(ODUrl first, ODUrl other) {
        assertEquals(first, other);
    }

    static Stream<Arguments> getParams() {
        String baseURL = "https://example.com/";
        return Stream.of(
                Arguments.of(baseURL + "issue tests", baseURL + "issue%20tests"),
                Arguments.of(baseURL + "issue tests?chpt=lesson 1 piano", baseURL + "issue " +
                        "tests?chpt=lesson%201%20piano"),
                Arguments.of(baseURL + "issue%20tests?chpt=lesson 1 piano",
                        baseURL + "issue%20tests?chpt=lesson%201%20piano")
        );
    }
}