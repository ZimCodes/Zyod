package xyz.zimtools.zyod.browsers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.remote.RemoteWebDriver;
import xyz.zimtools.zyod.args.Args;
import xyz.zimtools.zyod.fixtures.GlobalDefault;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

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
        RemoteWebDriver driver = DriverFactory.getDriver(args);
        Map<String, Object> caps = driver.getCapabilities().asMap();
        assertEquals(browserType, caps.get("browserName"), "An Incorrect driver was created from " +
                "the driver factory.");
    }
}