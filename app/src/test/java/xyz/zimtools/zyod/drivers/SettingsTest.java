package xyz.zimtools.zyod.drivers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import xyz.zimtools.zyod.Writer;
import xyz.zimtools.zyod.args.Args;
import xyz.zimtools.zyod.fixtures.Assert;
import xyz.zimtools.zyod.fixtures.DriversDefault;

import java.util.Map;

/**
 * Tests all webdriver settings to see if they've been applied
 */
@DisplayName("WebDriver Settings Test")
class SettingsTest {
    static String[] firefoxArgs = {"--headless", "--all-certs", DriversDefault.URL};
    static String[] noHeadlessArg = {"--download", "--all-certs", DriversDefault.URL};

    private void changeSettingName(StringBuilder builder, String settingName) {
        builder.replace(0, builder.length(), settingName);
    }

    private void defaultCapabilitiesAsserts(Map<String, Object> caps) {
        Boolean allCerts = (Boolean) caps.get("acceptInsecureCerts");
        Assert.settingTrue(allCerts, "acceptInsecureCerts");
    }

    /**
     * Tests webdriver and browser if all settings have been applied except '--headless.'
     * */
    @Test
    void firefoxSettings() {
        Args args = new Args(noHeadlessArg);
        FirefoxBrowser browser = new FirefoxBrowser(args);
        FirefoxDriver driver = browser.getDriver();
        Map<String, Object> caps = driver.getCapabilities().asMap();
        this.defaultCapabilitiesAsserts(caps);
        FirefoxProfile profile = browser.getProfile();
        StringBuilder setting = new StringBuilder("browser.download.dir");

        Assert.settingEquals(profile.getStringPreference(setting.toString(), ""),
                args.getArgsDownload().getDownloadDir().getPath(), setting.toString());

        changeSettingName(setting, "browser.helperApps.neverAsk.saveToDisk");
        Assert.settingEquals(profile.getStringPreference(setting.toString(), ""),
                Writer.readMimeFile(), setting.toString());

        changeSettingName(setting, "browser.download.folderList");
        Assert.settingEquals(profile.getIntegerPreference(setting.toString(), 1), 2, setting.toString());

        changeSettingName(setting, "pdfjs.disabled");
        Assert.settingEquals(profile.getBooleanPreference(setting.toString(), false), true,
                setting.toString());
    }

    /**
     * Tests if firefox webdriver '--headless' option has been set.*/
    @Test
    void firefoxHeadless() {
        Args args = new Args(firefoxArgs);
        FirefoxBrowser browser = new FirefoxBrowser(args);
        FirefoxDriver driver = browser.getDriver();
        Map<String, Object> caps = driver.getCapabilities().asMap();
        Assert.settingTrue((Boolean) caps.get("moz:headless"), "headless");
    }

    /**
     * NOTE: Unable to check if 'headless' & experimental options has been applied.
     */
    @Test
    void chromeSettings() {
        Args args = new Args(noHeadlessArg);
        ChromeBrowser browser = new ChromeBrowser(args);
        ChromeDriver driver = browser.getDriver();
        Map<String, Object> caps = driver.getCapabilities().asMap();

        this.defaultCapabilitiesAsserts(caps);
        // Check 'chrome://prefs-internals/' in browser to see if other options are applied.
        driver.navigate().to(DriversDefault.URL);
    }

    /**
     * NOTE: Unable to check if 'headless' & experimental options has been applied.
     */
    @Test
    void edgeSettings() {
        Args args = new Args(noHeadlessArg);
        EdgeBrowser browser = new EdgeBrowser(args);
        EdgeDriver driver = browser.getDriver();
        Map<String, Object> caps = driver.getCapabilities().asMap();

        this.defaultCapabilitiesAsserts(caps);

        // Check 'edge://prefs-internals' in browser to see if other options are applied.
        driver.navigate().to(DriversDefault.URL);
    }
}