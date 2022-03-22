package xyz.zimtools.zyod.support;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import xyz.zimtools.zyod.args.Args;
import xyz.zimtools.zyod.assets.info.NavInfoParser;
import xyz.zimtools.zyod.browsers.DriverFactory;
import xyz.zimtools.zyod.fixtures.ODDefault;
import xyz.zimtools.zyod.od.ODType;
import xyz.zimtools.zyod.od.navigators.NavType;

/**
 * Test simulated interactions with the browser.
 */
class InteractTest {
    private static RemoteWebDriver driver;
    private static WebElement element;

    @BeforeAll
    static void init() {
        Args args = new Args(new String[]{ODDefault.GO_INDEX});
        driver = DriverFactory.getDriver(args);
        driver.get(ODDefault.GO_INDEX);
        NavInfoParser parser = new NavInfoParser();
        element = NavSupport.getElements(driver,
                        parser.getInfo(ODType.GOINDEX.name(), NavType.GoIndex.LIST_VIEW.name()).getCssFileSelector())
                .get(4);
    }

    @AfterEach
    void after() throws InterruptedException {
        Thread.sleep(10000);
    }

    @Test
    void hover() {
        InteractSupport.hover(driver, element);
    }

    @Test
    void contextClick() {
        InteractSupport.rightClick(driver, element);
    }

    @Test
    void scrollDown() {
        InteractSupport.globalScrollDown(driver);
    }

    @Test
    void scrollToElement() {
        InteractSupport.scrollToElement(driver, element);
    }
}