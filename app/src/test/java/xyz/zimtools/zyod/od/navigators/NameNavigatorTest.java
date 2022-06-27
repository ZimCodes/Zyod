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


class NameNavigatorTest {
    private static final String[] MAIN_ARGS = {"--all-certs", "-d", "2"};
    private static Args args;
    private static RemoteWebDriver driver;
    private Navigator navigator;
    private static Directory directory;
    private static final String NAV_NAME = "name navigator";

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
    void zFile() {
        this.init(new String[]{ODDemoRef.ZFILE, "--ddir", DownloadDefault.DOWNLOAD_DIR,
                "--download", "--scroll"});
        navigator = new ZFileNav(driver, args);
        assertResourceCount(ODDemoRef.ZFILE, 12, 6);
        AppConfig.sleep(5000L);
        DownloadAssert.fileExists();
    }
}