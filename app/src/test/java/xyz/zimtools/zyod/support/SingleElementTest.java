package xyz.zimtools.zyod.support;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import xyz.zimtools.zyod.args.Args;
import xyz.zimtools.zyod.browsers.DriverFactory;
import xyz.zimtools.zyod.fixtures.GlobalDefault;
import xyz.zimtools.zyod.fixtures.ODDefault;
import xyz.zimtools.zyod.fixtures.asserts.SupportAssert;
import xyz.zimtools.zyod.od.ODType;
import xyz.zimtools.zyod.od.navigators.NavInfoParser;
import xyz.zimtools.zyod.od.navigators.NavType;

import java.util.Optional;

/**
 * Test to retrieve a single element from each OD.
 */
class SingleElementTest {
    private static final String[] MAIN_ARGS = {"--driver", "chrome", "--headless"};
    private static NavInfoParser navInfoParser;
    private Args args;
    private RemoteWebDriver driver;

    @BeforeAll
    static void initDirector() {
        navInfoParser = new NavInfoParser();
    }

    private void initArgs(String[][] cmdArgs) {
        String[] combinedArgs = GlobalDefault.joinArr(cmdArgs);
        args = new Args(combinedArgs);
        driver = DriverFactory.getDriver(args);
    }

    private void assertElement(String url, String cssSelector) {
        String[] extraArgs = {url};
        this.initArgs(new String[][]{MAIN_ARGS, extraArgs});
        driver.get(args.getArgsMain().getUrls().get(0));
        Optional<WebElement> element = NavSupport.getElement(driver, cssSelector);
        SupportAssert.elementExists(element, url);
        driver.quit();
    }

    @Test
    void alistOriginal() {
        this.assertElement(ODDefault.ALIST,
                navInfoParser.getNavInfo(ODType.ALIST.name(), NavType.AList.ORIGINAL.name()).getCssFileSelector());
    }

    @ParameterizedTest
    @ValueSource(strings = {ODDefault.GO_INDEX_1, ODDefault.GO_INDEX_2})
    void goIndexListView(String url) {
        this.assertElement(url,
                navInfoParser.getNavInfo(ODType.GOINDEX.name(), NavType.GoIndex.LIST_VIEW.name()).getCssFileSelector());
    }

    @Test
    void gdIndexMain() {
        this.assertElement(ODDefault.GD_INDEX, navInfoParser.getNavInfo(ODType.GDINDEX.name(),
                NavType.GDIndex.MAIN.name()).getCssFileSelector());
    }

    @Test
    void fodiMain() {
        this.assertElement(ODDefault.FODI, navInfoParser.getNavInfo(ODType.FODI.name(),
                NavType.FODI.MAIN.name()).getCssFileSelector());
    }

    @Test
    void zfileMain() {
        this.assertElement(ODDefault.ZFILE, navInfoParser.getNavInfo(ODType.ZFILE.name(),
                NavType.ZFile.MAIN.name()).getCssFileSelector());
    }

    @Test
    void onedriveVercelIndexMain() {
        this.assertElement(ODDefault.ONEDRIVE_VERCEL_INDEX,
                navInfoParser.getNavInfo(ODType.ONEDRIVE_VERCEL_INDEX.name(),
                        NavType.Onedrive_Vercel_Index.MAIN.name()).getCssFileSelector());
    }

}