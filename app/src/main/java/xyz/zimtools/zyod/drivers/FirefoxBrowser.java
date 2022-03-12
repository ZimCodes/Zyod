package xyz.zimtools.zyod.drivers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.RemoteWebDriver;
import xyz.zimtools.zyod.Writer;
import xyz.zimtools.zyod.args.ArgsDownload;
import xyz.zimtools.zyod.args.ArgsWebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.Map;

public class FirefoxBrowser extends Browser {
    private final FirefoxOptions options;
    private final FirefoxProfile profile;

    public FirefoxBrowser(ArgsWebDriver argsWebDriver, ArgsDownload argsDownload) {
        super(argsWebDriver, argsDownload);
        this.options = new FirefoxOptions();
        this.profile = new FirefoxProfile();
        this.setCapabilities();
        this.setPreferences();
    }

    @Override
    protected void setPreferences() {
        this.options.setHeadless(this.argsWebDriver.isHeadless()); // set headless mode
        if (this.argsDownload.isDownloading()) {
            this.argsDownload.getDownloadDir().mkdirs(); // Create none existent directories, if any
            this.profile.setPreference("browser.download.dir", argsDownload.getDownloadDir().getAbsolutePath());
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
        if ("auto".equals(this.argsWebDriver.getDriverVersion())) {
            WebDriverManager.getInstance(FirefoxDriver.class).setup();
        } else {
            WebDriverManager.getInstance(FirefoxDriver.class).driverVersion(this.argsWebDriver.getDriverVersion()).setup();
        }
        return new FirefoxDriver(this.options);
    }

    FirefoxProfile getProfile() {
        return this.profile;
    }
}