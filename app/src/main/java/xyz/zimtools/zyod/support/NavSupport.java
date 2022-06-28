package xyz.zimtools.zyod.support;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
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
    private final static long UNTIL_WAIT = 10000L;
    public final static long ID_WAIT_IMPLICIT = 1L;

    public static List<WebElement> getWaitElements(RemoteWebDriver driver, String cssSelect, long wait) {
        List<WebElement> elements = List.of();
        try {
            elements =
                    new WebDriverWait(driver, Duration.ofMillis(wait)).until((el) -> el.findElements(By.cssSelector(cssSelect)));
        } catch (TimeoutException e) {
            printError(driver);
        }
        return elements;
    }

    public static List<WebElement> getElements(RemoteWebDriver driver, String cssSelect) {
        return getWaitElements(driver, cssSelect, UNTIL_WAIT);
    }

    /**
     * Retrieve Web elements using the implicit wait time for finding ID
     * */
    public static List<WebElement> getIDElements(RemoteWebDriver driver, String cssSelect) {
        return getWaitElements(driver, cssSelect, ID_WAIT_IMPLICIT);
    }

    /**
     * Gets the first {@link WebElement} found on a webpage.
     *
     * @param driver    {@link RemoteWebDriver}
     * @param cssSelect the css selector pointing to a WebElement
     */
    public static Optional<WebElement> getWaitElement(RemoteWebDriver driver, String cssSelect, long wait) {
        Optional<WebElement> element = Optional.empty();
        try {
            element =
                    Optional.of(new WebDriverWait(driver, Duration.ofMillis(wait)).until((el) -> el.findElement(By.cssSelector(cssSelect))));
        } catch (TimeoutException | NoSuchElementException e) {
            printError(driver);
        }
        return element;
    }

    public static Optional<WebElement> getElement(RemoteWebDriver driver, String cssSelect) {
        return getWaitElement(driver, cssSelect, UNTIL_WAIT);
    }

    /**
     * Retrieve Web element using the implicit wait time for finding ID
     * */
    public static Optional<WebElement> getIDElement(RemoteWebDriver driver, String cssSelect) {
        return getWaitElement(driver, cssSelect, ID_WAIT_IMPLICIT);
    }

    private static void printError(RemoteWebDriver driver) {
        System.out.printf("Web element(s) cannot be found in %s!%n", driver.getCurrentUrl());
    }
}