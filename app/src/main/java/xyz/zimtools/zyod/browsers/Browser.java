package xyz.zimtools.zyod.browsers;

import org.openqa.selenium.remote.RemoteWebDriver;
import xyz.zimtools.zyod.args.Args;

import java.util.Map;
import java.util.HashMap;

/**
 * Constructs a Generic Web Driver.
 */
public abstract class Browser {
    protected final Map<String, Object> capabilities;
    protected final Args args;

    public Browser(Args args) {
        this.capabilities = new HashMap<>();
        this.args = args;
    }

    protected abstract void setPreferences();

    protected void setCapabilities() {
        this.capabilities.put("acceptInsecureCerts", this.args.getArgsWebDriver().isAllCerts());
    }

    public abstract RemoteWebDriver getDriver();
}