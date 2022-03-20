package xyz.zimtools.zyod.support;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

/**
 * Helper methods to retrieve {@link WebElement} from a webpage
 */
public class NavSupport {
    private final static long UNTIL_WAIT = 30000L;

    public static List<WebElement> getElements(RemoteWebDriver driver, String cssSelect) {
        List<WebElement> elements = List.of();
        try {
            elements =
                    new WebDriverWait(driver, Duration.ofMillis(UNTIL_WAIT)).until((el) -> el.findElements(By.cssSelector(cssSelect)));
        } catch (TimeoutException e) {
            System.out.printf("Element(s) cannot be found! %s might not be " +
                            "fully loaded for the element(s) to make an appearance. Or page is " +
                            "empty.%n",
                    driver.getCurrentUrl());
        }
        return elements;
    }

    /**
     * Gets the first {@link WebElement} found on a webpage.
     *
     * @param driver    {@link RemoteWebDriver}
     * @param cssSelect the css selector pointing to a WebElement
     */
    public static Optional<WebElement> getElement(RemoteWebDriver driver, String cssSelect) {
        List<WebElement> elements = getElements(driver, cssSelect);
        return elements.isEmpty() ? Optional.empty() : Optional.of(elements.get(0));
    }
}