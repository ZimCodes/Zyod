package xyz.zimtools.zyod.browsers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.remote.RemoteWebDriver;
import xyz.zimtools.zyod.args.Args;
import xyz.zimtools.zyod.fixtures.GlobalDefault;

import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests the creation of different driver types
 */
class FactoryTest {
    private static final String[] MAIN_ARGS = {GlobalDefault.URL, "--headless", "--driver", ""};

    @ParameterizedTest
    @ValueSource(strings = {"firefox", "chrome", "msedge"})
    void driverCreation(String browserType) {
        MAIN_ARGS[3] = browserType;
        Args args = new Args(MAIN_ARGS);
        RemoteWebDriver driver = BrowserFactory.getBrowser(args).getDriver();
        Map<String, Object> caps = driver.getCapabilities().asMap();
        assertEquals(browserType, caps.get("browserName"), "An Incorrect driver was created from " +
                "the driver factory.");
        driver.close();
    }
}