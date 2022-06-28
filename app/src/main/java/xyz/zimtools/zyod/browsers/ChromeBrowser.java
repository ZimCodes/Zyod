package xyz.zimtools.zyod.browsers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import xyz.zimtools.zyod.args.Args;
import xyz.zimtools.zyod.support.NavSupport;

import java.time.Duration;
import java.util.Map;

public class ChromeBrowser extends ChromiumBrowser {
    private final ChromeOptions options;

    public ChromeBrowser(Args args) {
        super(args);
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
        this.options.setHeadless(this.args.getArgsWebDriver().isHeadless());
        this.options.setPageLoadTimeout(Duration.ofMillis(this.args.getArgsMisc().getPageWait()));
        if (this.args.getArgsDownload().isDownloading()) {
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
        this.options.setImplicitWaitTimeout(Duration.ofMillis(this.args.getArgsMisc().getImplicitWait()));
        return prepareDriver();
    }

    @Override
    public ChromeDriver getIDDriver() {
        this.options.setImplicitWaitTimeout(Duration.ofMillis(NavSupport.ID_WAIT_IMPLICIT));
        return prepareDriver();
    }

    @Override
    protected ChromeDriver prepareDriver() {
        if ("auto".equals(this.args.getArgsWebDriver().getDriverVersion())) {
            WebDriverManager.getInstance(ChromeDriver.class).setup();
        } else {
            WebDriverManager.getInstance(ChromeDriver.class).driverVersion(this.args.getArgsWebDriver().getDriverVersion()).setup();
        }
        return new ChromeDriver(this.options);
    }
}