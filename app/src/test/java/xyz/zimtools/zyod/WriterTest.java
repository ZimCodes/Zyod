package xyz.zimtools.zyod;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.RemoteWebDriver;
import xyz.zimtools.zyod.args.Args;
import xyz.zimtools.zyod.assets.Directory;
import xyz.zimtools.zyod.assets.ODUrl;
import xyz.zimtools.zyod.browsers.BrowserFactory;
import xyz.zimtools.zyod.fixtures.GlobalDefault;
import xyz.zimtools.zyod.fixtures.ODDemoRef;
import xyz.zimtools.zyod.od.navigators.GDIndexNav;
import xyz.zimtools.zyod.od.navigators.Navigator;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class WriterTest {
    private static final String[] MAIN_ARGS = {"--all-certs", "-d", "2"};
    private static Args args;
    private static RemoteWebDriver driver;
    private static Directory directory;

    void init(String[] extraArgs) {
        String url = extraArgs[0];
        String[] newArgs = GlobalDefault.joinArr(new String[][]{extraArgs, MAIN_ARGS});
        args = new Args(newArgs);
        driver = BrowserFactory.getBrowser(args).getDriver();
        directory = new Directory(0, new ODUrl(url));
    }

    /**
     * Test the retrieval of mime types from a JSON file.
     */
    @Test
    @DisplayName("Get Mime Types")
    void readMimeFile() {
        assertNotEquals(Writer.readMimeFile(), "");
    }

    /**
     * Test recording feature of Zyod
     */
    @Test
    void recordToFile() {
        this.init(new String[]{ODDemoRef.GD_INDEX});
        Navigator navigator = new GDIndexNav(driver, args);
        navigator.navigate(directory);
        File file = new File(GlobalDefault.RES_DIR + AppConfig.SEP + "output.txt");
        Writer.recordFiles(file, navigator.getFileResults());
        int total = Writer.readInputFile(file).size();
        int answer = 4;
        assertEquals(total, answer, String.format("Recording failed! Output file contains %d " +
                "items instead of %d", total, answer));
        driver.quit();
        file.delete();
    }
}