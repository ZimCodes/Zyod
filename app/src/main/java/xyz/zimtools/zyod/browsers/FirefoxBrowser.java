package xyz.zimtools.zyod.browsers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import xyz.zimtools.zyod.Writer;
import xyz.zimtools.zyod.args.Args;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;
import java.util.Map;

public class FirefoxBrowser extends Browser {
    private final FirefoxOptions options;
    private final FirefoxProfile profile;

    public FirefoxBrowser(Args args) {
        super(args);
        this.options = new FirefoxOptions();
        this.profile = new FirefoxProfile();
        this.setCapabilities();
        this.setPreferences();
    }

    @Override
    protected void setPreferences() {
        this.options.setHeadless(this.args.getArgsWebDriver().isHeadless()); // set headless mode
        this.options.setImplicitWaitTimeout(Duration.ofMillis(this.args.getArgsMisc().getImplicitWait()));
        this.options.setPageLoadTimeout(Duration.ofMillis(this.args.getArgsMisc().getPageWait()));
        if (this.args.getArgsDownload().isDownloading()) {
            this.args.getArgsDownload().getDownloadDir().mkdirs(); // Create none existent directories, if any
            this.profile.setPreference("browser.download.dir",
                    this.args.getArgsDownload().getDownloadDir().getAbsolutePath());
            this.profile.setPreference("browser.download.folderList", 2);
        }
        this.profile.setPreference("browser.helperApps.neverAsk.saveToDisk", Writer.readMimeFile());
        this.profile.setPreference("pdfjs.disabled", true);
        this.options.setProfile(this.profile);
    }

    @Override
    protected void setCapabilities() {
        super.setCapabilities();
        for (Map.Entry<String, Object> entry : this.capabilities.entrySet()) {
            this.options.setCapability(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public FirefoxDriver getDriver() {
        if ("auto".equals(this.args.getArgsWebDriver().getDriverVersion())) {
            WebDriverManager.getInstance(FirefoxDriver.class).setup();
        } else {
            WebDriverManager.getInstance(FirefoxDriver.class).driverVersion(this.args.getArgsWebDriver().getDriverVersion()).setup();
        }
        return new FirefoxDriver(this.options);
    }

    FirefoxProfile getProfile() {
        return this.profile;
    }
}