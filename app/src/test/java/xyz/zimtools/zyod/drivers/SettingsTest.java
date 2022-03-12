package xyz.zimtools.zyod.drivers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import xyz.zimtools.zyod.Writer;
import xyz.zimtools.zyod.args.ArgsDownload;
import xyz.zimtools.zyod.args.ArgsWebDriver;
import xyz.zimtools.zyod.fixtures.ArgsDefault;
import xyz.zimtools.zyod.fixtures.Assert;
import xyz.zimtools.zyod.fixtures.DriversDefault;

import java.util.Map;

/**
 * Tests all webdriver settings to see if they've been applied
 */
@DisplayName("WebDriver Settings Test")
class SettingsTest {
    static ArgsDownload argsDownload;
    static ArgsWebDriver argsWebDriver;
    static String[] firefoxArgs = {"--headless", "--download", "--all-certs"};
    static String[] chromiumArgs = {"--download", "--all-certs"};

    @BeforeEach
    void initArgs() {
        argsDownload = new ArgsDownload();
        argsWebDriver = new ArgsWebDriver();
    }

    private void parseArgs(String[] args) {
        ArgsDefault.argParse(args, argsWebDriver, argsDownload);
    }

    private void changeSettingName(StringBuilder builder, String settingName) {
        builder.replace(0, builder.length(), settingName);
    }

    private void defaultCapabilitiesAsserts(Map<String, Object> caps) {
        Boolean allCerts = (Boolean) caps.get("acceptInsecureCerts");
        Assert.settingTrue(allCerts, "acceptInsecureCerts");
    }

    @Test
    void firefoxSettings() {
        this.parseArgs(firefoxArgs);
        FirefoxBrowser browser = new FirefoxBrowser(argsWebDriver, argsDownload);
        FirefoxDriver driver = browser.getDriver();
        Map<String, Object> caps = driver.getCapabilities().asMap();
        this.defaultCapabilitiesAsserts(caps);
        Assert.settingTrue((Boolean) caps.get("moz:headless"), "headless");
        FirefoxProfile profile = browser.getProfile();
        StringBuilder setting = new StringBuilder("browser.download.dir");

        Assert.settingEquals(profile.getStringPreference(setting.toString(), ""),
                argsDownload.getDownloadDir().getPath(), setting.toString());

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
     * NOTE: Unable to check if 'headless' & experimental options has been applied.
     */
    @Test
    void chromeSettings() {
        this.parseArgs(chromiumArgs);
        ChromeBrowser browser = new ChromeBrowser(argsWebDriver, argsDownload);
        ChromeDriver driver = browser.getDriver();
        Map<String, Object> caps = driver.getCapabilities().asMap();

        this.defaultCapabilitiesAsserts(caps);
        // Check 'chrome://prefs-internals/' in browser to see if other options are applied.
        driver.navigate().to(DriversDefault.URL);
    }

    @Test
    void edgeSettings() {
        this.parseArgs(chromiumArgs);
        EdgeBrowser browser = new EdgeBrowser(argsWebDriver, argsDownload);
        EdgeDriver driver = browser.getDriver();
        Map<String, Object> caps = driver.getCapabilities().asMap();

        this.defaultCapabilitiesAsserts(caps);

        // Check 'edge://prefs-internals' in browser to see if other options are applied
        driver.navigate().to(DriversDefault.URL);
    }
}