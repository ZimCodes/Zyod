package xyz.zimtools.zyod.drivers;

import org.openqa.selenium.remote.RemoteWebDriver;
import xyz.zimtools.zyod.args.ArgsDownload;
import xyz.zimtools.zyod.args.ArgsWebDriver;

import java.util.Map;
import java.util.HashMap;

/**
 * Constructs a Generic Web Driver.
 */
public abstract class Browser {
    protected final Map<String, Object> capabilities;
    protected final ArgsWebDriver argsWebDriver;
    protected final ArgsDownload argsDownload;

    public Browser(ArgsWebDriver argsWebDriver, ArgsDownload argsDownload) {
        this.capabilities = new HashMap<>();
        this.argsDownload = argsDownload;
        this.argsWebDriver = argsWebDriver;
    }

    protected abstract void setPreferences();

    protected void setCapabilities() {
        this.capabilities.put("acceptInsecureCerts", this.argsWebDriver.isAllCerts());
    }

    public abstract RemoteWebDriver getDriver();
}