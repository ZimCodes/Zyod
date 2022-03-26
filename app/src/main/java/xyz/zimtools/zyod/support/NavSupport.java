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
    private final static long UNTIL_WAIT = 20000L;

    public static List<WebElement> getElements(RemoteWebDriver driver, String cssSelect) {
        List<WebElement> elements = List.of();
        try {
            elements =
                    new WebDriverWait(driver, Duration.ofMillis(UNTIL_WAIT)).until((el) -> el.findElements(By.cssSelector(cssSelect)));
        } catch (TimeoutException e) {
            printError(driver);
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
        Optional<WebElement> element = Optional.empty();
        try {
            element =
                    Optional.of(new WebDriverWait(driver, Duration.ofMillis(UNTIL_WAIT)).until((el) -> el.findElement(By.cssSelector(cssSelect))));
        } catch (TimeoutException | NoSuchElementException e) {
            printError(driver);
        }
        return element;
    }

    private static void printError(RemoteWebDriver driver) {
        System.out.printf("Element(s) cannot be found! %s might not be " +
                        "fully loaded for the element(s) to make an appearance. Or page is " +
                        "empty.%n",
                driver.getCurrentUrl());
    }
}