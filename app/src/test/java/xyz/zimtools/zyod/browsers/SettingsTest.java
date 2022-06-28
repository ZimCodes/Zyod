package xyz.zimtools.zyod.browsers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import xyz.zimtools.zyod.Writer;
import xyz.zimtools.zyod.args.Args;
import xyz.zimtools.zyod.fixtures.GlobalDefault;
import xyz.zimtools.zyod.fixtures.asserts.BrowserAssert;

import java.util.Map;

/**
 * Tests all webdriver settings to see if they've been applied
 */
@DisplayName("WebDriver Settings Test")
class SettingsTest {
    private static final String[] MAIN_ARGS = {"--download", "--all-certs"};
    private static final String[] HEADLESS_ARGS = {"--headless"};
    private static final String[] URL_ARG = {GlobalDefault.URL};

    private void changeSettingName(StringBuilder builder, String settingName) {
        builder.replace(0, builder.length(), settingName);
    }

    private void defaultCapabilitiesAsserts(Map<String, Object> caps) {
        Boolean allCerts = (Boolean) caps.get("acceptInsecureCerts");
        BrowserAssert.settingTrue(allCerts, "acceptInsecureCerts");
    }

    /**
     * Tests webdriver and browser if all settings have been applied except '--headless.'
     */
    @Test
    void firefoxSettings() {
        String[] combinedArgs = GlobalDefault.joinArr(new String[][]{MAIN_ARGS, URL_ARG});
        Args args = new Args(combinedArgs);
        FirefoxBrowser browser = new FirefoxBrowser(args);
        FirefoxDriver driver = browser.getDriver();
        Map<String, Object> caps = driver.getCapabilities().asMap();
        this.defaultCapabilitiesAsserts(caps);
        FirefoxProfile profile = browser.getProfile();
        StringBuilder setting = new StringBuilder("browser.download.dir");

        BrowserAssert.settingEquals(profile.getStringPreference(setting.toString(), ""),
                args.getArgsDownload().getDownloadDir().getPath(), setting.toString());

        changeSettingName(setting, "browser.helperApps.neverAsk.saveToDisk");
        BrowserAssert.settingEquals(profile.getStringPreference(setting.toString(), ""),
                Writer.readMimeFile(), setting.toString());

        changeSettingName(setting, "browser.download.folderList");
        BrowserAssert.settingEquals(profile.getIntegerPreference(setting.toString(), 1), 2, setting.toString());

        changeSettingName(setting, "pdfjs.disabled");
        BrowserAssert.settingEquals(profile.getBooleanPreference(setting.toString(), false), true,
                setting.toString());

        changeSettingName(setting, "media.play-stand-alone");
        BrowserAssert.settingEquals(profile.getBooleanPreference(setting.toString(), true), false,
                setting.toString());
        driver.close();
    }

    /**
     * Tests if firefox webdriver '--headless' option has been set.
     */
    @Test
    void firefoxHeadless() {
        String[] combinedArgs = GlobalDefault.joinArr(new String[][]{HEADLESS_ARGS, URL_ARG});
        Args args = new Args(combinedArgs);
        FirefoxBrowser browser = new FirefoxBrowser(args);
        FirefoxDriver driver = browser.getDriver();
        Map<String, Object> caps = driver.getCapabilities().asMap();
        BrowserAssert.settingTrue((Boolean) caps.get("moz:headless"), "headless");
        driver.close();
    }

    /**
     * NOTE: Unable to check if 'headless' & experimental options has been applied. Check manually!
     */
    @Test
    void chromeSettings() {
        String[] combinedArgs = GlobalDefault.joinArr(new String[][]{MAIN_ARGS, URL_ARG});
        Args args = new Args(combinedArgs);
        ChromeBrowser browser = new ChromeBrowser(args);
        ChromeDriver driver = browser.getDriver();
        Map<String, Object> caps = driver.getCapabilities().asMap();

        this.defaultCapabilitiesAsserts(caps);
        // Check 'chrome://prefs-internals/' in browser to see if other options are applied.
        //driver.navigate().to(GlobalDefault.URL);
        driver.close();
    }

    /**
     * NOTE: Unable to check if 'headless' & experimental options has been applied. Check manually!
     */
    @Test
    void msedgeSettings() {
        String[] combinedArgs = GlobalDefault.joinArr(new String[][]{MAIN_ARGS, URL_ARG});
        Args args = new Args(combinedArgs);
        MSEdgeBrowser browser = new MSEdgeBrowser(args);
        EdgeDriver driver = browser.getDriver();
        Map<String, Object> caps = driver.getCapabilities().asMap();

        this.defaultCapabilitiesAsserts(caps);

        // Check 'edge://prefs-internals' in browser to see if other options are applied.
        //driver.navigate().to(GlobalDefault.URL);
        driver.close();
    }
}