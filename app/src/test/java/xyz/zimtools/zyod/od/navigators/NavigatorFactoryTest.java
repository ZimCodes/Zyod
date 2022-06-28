package xyz.zimtools.zyod.od.navigators;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.remote.RemoteWebDriver;
import xyz.zimtools.zyod.AppConfig;
import xyz.zimtools.zyod.args.Args;
import xyz.zimtools.zyod.browsers.BrowserFactory;
import xyz.zimtools.zyod.fixtures.GlobalDefault;
import xyz.zimtools.zyod.fixtures.ODDemoRef;
import xyz.zimtools.zyod.od.ODType;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class NavigatorFactoryTest {
    private static final String[] MAIN_ARGS = {"--all-certs"};
    private static Args args;
    private static RemoteWebDriver driver;

    @AfterEach
    void cleanUp() {
        driver.quit();
    }

    void init(String[] extraArgs) {
        String url = extraArgs[0];
        String[] newArgs = GlobalDefault.joinArr(new String[][]{extraArgs, MAIN_ARGS});
        args = new Args(newArgs);
        driver = BrowserFactory.getBrowser(args).getIDDriver();
        driver.get(url);
        AppConfig.sleep(10000L);
    }

    @ParameterizedTest
    @MethodSource("getParams")
    void factoryTest(String url, ODType expectedODType) {
        this.init(new String[]{url});
        Navigator navigator = NavigatorFactory.getNavigator(driver, args);
        assertNotNull(navigator, String.format("Navigator factory was not able to create an appropriate " +
                "navigator for %s at %s.", expectedODType.name(), url));
        assertEquals(expectedODType.name(), navigator.getId(), String.format("Navigator factory " +
                "created the incorrect navigator type for %s at %s.", expectedODType.name(), url));
    }

    static Stream<Arguments> getParams() {
        return Stream.of(
                Arguments.of(ODDemoRef.ZFILE, ODType.ZFILE),
                Arguments.of(ODDemoRef.ONEDRIVE_VERCEL_INDEX, ODType.ONEDRIVE_VERCEL_INDEX),
                Arguments.of(ODDemoRef.GO_INDEX, ODType.GOINDEX),
                Arguments.of(ODDemoRef.GD_INDEX, ODType.GDINDEX),
                Arguments.of(ODDemoRef.ALIST, ODType.ALIST),
                Arguments.of(ODDemoRef.FODI, ODType.FODI)
        );
    }
}