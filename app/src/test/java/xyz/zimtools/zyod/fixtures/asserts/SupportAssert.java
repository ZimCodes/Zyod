package xyz.zimtools.zyod.fixtures.asserts;

import org.openqa.selenium.WebElement;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SupportAssert {
    public static void elementExists(Optional<WebElement> element, String url) {
        assertTrue(element.isPresent(), String.format("No element can be found at %s. Try waiting" +
                " a bit longer for the page to load before grabbing elements.", url));
    }
}