package xyz.zimtools.zyod.drivers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import xyz.zimtools.zyod.args.ArgsDownload;
import xyz.zimtools.zyod.args.ArgsWebDriver;

import java.util.Map;

public class ChromeBrowser extends ChromiumBrowser {
    private final ChromeOptions options;

    public ChromeBrowser(ArgsWebDriver argsWebDriver, ArgsDownload argsDownload) {
        super(argsWebDriver, argsDownload);
        this.options = new ChromeOptions();
        this.setCapabilities();
        this.setPreferences();
    }

    @Override
    protected void setCapabilities() {
        super.setCapabilities();
        for (Map.Entry<String, Object> entry : this.capabilities.entrySet()) {
            this.options.setCapability(entry.getKey(), entry.getValue());
        }
    }

    @Override
    protected void setPreferences() {
        this.options.setHeadless(this.argsWebDriver.isHeadless());
        if (this.argsDownload.isDownloading()) {
            super.setPreferences();
        }

        if (!this.prefs.isEmpty()) {
            this.options.setExperimentalOption("prefs", this.prefs);
        }
    }

    /**
     * Retrieve a prepared ChromeDriver.
     * <p>
     * Selenium only supports certain versions of ChromeDriver.
     * Refer to
     * <a href="https://github.com/SeleniumHQ/selenium/blob/trunk/java/CHANGELOG">Selenium Changelog</a>
     */
    @Override
    public ChromeDriver getDriver() {
        if ("auto".equals(this.argsWebDriver.getDriverVersion())) {
            WebDriverManager.getInstance(ChromeDriver.class).setup();
        } else {
            WebDriverManager.getInstance(ChromeDriver.class).driverVersion(this.argsWebDriver.getDriverVersion()).setup();
        }
        return new ChromeDriver(this.options);
    }

}