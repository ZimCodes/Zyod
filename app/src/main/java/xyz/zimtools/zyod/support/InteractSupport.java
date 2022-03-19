package xyz.zimtools.zyod.support;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Helper methods for simulating interactivity with a webdriver
 * */
public class InteractSupport {
    public static void rightClick(RemoteWebDriver driver, WebElement webElement) {
        new Actions(driver).contextClick(webElement).perform();
    }

    public static void hover(RemoteWebDriver driver, WebElement webElement) {
        new Actions(driver).moveToElement(webElement).perform();
    }

    /**
     * Scroll to the bottom of the current page using the scrollbar in the {@code <body>} element.
     *
     * @param driver {@link RemoteWebDriver}
     */
    public static void globalScrollDown(RemoteWebDriver driver) {
        script(driver, "window.scrollBy(0,document.body.scrollHeight);");
    }

    /**
     * Scroll until {@link WebElement} is visible.
     *
     * @param driver     {@link RemoteWebDriver}
     * @param webElement {@link WebElement} to scroll to.
     */
    public static void scrollToElement(RemoteWebDriver driver, WebElement webElement) {
        script(driver, "arguments[0].scrollIntoView(true);", webElement);
    }

    /**
     * Execute JavaScript code.
     *
     * @param driver {@link RemoteWebDriver}
     * @param jsCode JavaScript code to execute
     * @param jsArgs arguments to add to the JavaScript code
     */
    public static void script(RemoteWebDriver driver, String jsCode, Object... jsArgs) {
        driver.executeScript(jsCode, jsArgs);
    }
}