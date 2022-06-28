package xyz.zimtools.zyod.od.identifiers;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.RemoteWebDriver;
import xyz.zimtools.zyod.AppConfig;
import xyz.zimtools.zyod.args.Args;
import xyz.zimtools.zyod.browsers.BrowserFactory;
import xyz.zimtools.zyod.fixtures.GlobalDefault;
import xyz.zimtools.zyod.fixtures.ODDemoRef;
import xyz.zimtools.zyod.fixtures.asserts.IDAssert;
import xyz.zimtools.zyod.od.ODType;

/**
 * Tests if ODs can be identified correctly
 */
class IDTest {
    private static final String[] MAIN_ARGS = {"--headless"};
    private static RemoteWebDriver driver;

    private void init(String[] extraArgs) {
        Args args = new Args(GlobalDefault.joinArr(new String[][]{extraArgs, MAIN_ARGS}));
        driver = BrowserFactory.getBrowser(args).getDriver();
        driver.get(extraArgs[0]);
        AppConfig.sleep(10000L);
    }

    @AfterAll
    static void close() {
        driver.quit();
    }

    //NOTE: OD has a chance of not loading properly without a refresh
    @Test
    void goIndex() {
        this.init(new String[]{ODDemoRef.GO_INDEX});
        IDAssert.isOD(new GOIndexID(driver), ODType.GOINDEX);
    }

    @Test
    void gdIndex() {
        this.init(new String[]{ODDemoRef.GD_INDEX});
        IDAssert.isOD(new GDIndexID(driver), ODType.GDINDEX);
    }

    @Test
    void fodi() {
        this.init(new String[]{ODDemoRef.FODI});
        IDAssert.isOD(new FODIID(driver), ODType.FODI);
    }

    @Test
    void zfile() {
        this.init(new String[]{ODDemoRef.ZFILE});
        IDAssert.isOD(new ZFileID(driver), ODType.ZFILE);
    }

    @Test
    void onedriveVercelIndex() {
        this.init(new String[]{ODDemoRef.ONEDRIVE_VERCEL_INDEX});
        IDAssert.isOD(new OneDriveVercelIndexID(driver), ODType.ONEDRIVE_VERCEL_INDEX);
    }

    @Test
    void alist() {
        this.init(new String[]{ODDemoRef.ALIST});
        IDAssert.isOD(new AListID(driver), ODType.ALIST);
    }
}