package xyz.zimtools.zyod.od.navigators;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.RemoteWebDriver;
import xyz.zimtools.zyod.AppConfig;
import xyz.zimtools.zyod.args.Args;
import xyz.zimtools.zyod.assets.Directory;
import xyz.zimtools.zyod.assets.ODUrl;
import xyz.zimtools.zyod.browsers.BrowserFactory;
import xyz.zimtools.zyod.fixtures.DownloadDefault;
import xyz.zimtools.zyod.fixtures.GlobalDefault;
import xyz.zimtools.zyod.fixtures.ODDemoRef;
import xyz.zimtools.zyod.fixtures.asserts.DownloadAssert;
import xyz.zimtools.zyod.fixtures.asserts.NavAssert;


class ODNavigatorTest {
    private static final String[] MAIN_ARGS = {"--all-certs", "-d", "2"};
    private static Args args;
    private static RemoteWebDriver driver;
    private Navigator navigator;
    private static Directory directory;
    private static final String NAV_NAME = "od navigator";

    @AfterAll
    static void cleanUp() {
        driver.quit();
        DownloadDefault.cleanDownloadDir();
    }

    void init(String[] extraArgs) {
        String url = extraArgs[0];
        String[] newArgs = GlobalDefault.joinArr(new String[][]{extraArgs, MAIN_ARGS});
        args = new Args(newArgs);
        driver = BrowserFactory.getBrowser(args).getDriver();
        directory = new Directory(0, new ODUrl(url));
    }

    private void assertResourceCount(String url, int expectedDirs, int expectedFiles) {
        navigator.navigate(directory);
        int totalDirs = navigator.getDirResults().size();
        int totalFiles = navigator.getFileResults().size();
        NavAssert.resourceCount(expectedDirs, totalDirs, NAV_NAME, "directories", url);
        NavAssert.resourceCount(expectedFiles, totalFiles, NAV_NAME, "files", url);
    }

    @Test
    void alist() {
        this.init(new String[]{ODDemoRef.ALIST + "/storj", "--ddir", DownloadDefault.DOWNLOAD_DIR,
                "--download"});
        navigator = new AListNav(driver, args);
        assertResourceCount(ODDemoRef.ALIST, 1, 1);
        AppConfig.sleep(5000L);
        DownloadAssert.fileExists();
    }

    @Test
    void gdindex() {
        this.init(new String[]{ODDemoRef.GD_INDEX, "--ddir", DownloadDefault.DOWNLOAD_DIR, "--download"});
        navigator = new GDIndexNav(driver, args);
        assertResourceCount(ODDemoRef.GD_INDEX, 5, 4);
        DownloadAssert.fileExists();
    }

    @Test
    void goindex() {
        this.init(new String[]{ODDemoRef.GO_INDEX, "--ddir", DownloadDefault.DOWNLOAD_DIR, "--download"});
        navigator = new GoIndexNav(driver, args);
        assertResourceCount(ODDemoRef.GO_INDEX, 9, 1);
        DownloadAssert.fileExists();
    }

    @Test
    void onedrivevercelindex() {
        this.init(new String[]{ODDemoRef.ONEDRIVE_VERCEL_INDEX, "--ddir", DownloadDefault.DOWNLOAD_DIR, "--download"});
        navigator = new OneDriveVercelIndexNav(driver, args);
        assertResourceCount(ODDemoRef.ONEDRIVE_VERCEL_INDEX, 9, 1);
        DownloadAssert.fileExists();
    }
}