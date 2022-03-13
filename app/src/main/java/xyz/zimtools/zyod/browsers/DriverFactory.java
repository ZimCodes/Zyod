package xyz.zimtools.zyod.browsers;

import org.openqa.selenium.remote.RemoteWebDriver;
import xyz.zimtools.zyod.args.Args;
/**
 * Creates all available drivers for use.
 * */
public final class DriverFactory {
    public static RemoteWebDriver getDriver(Args args) {
        BrowserType type =
                BrowserType.valueOf(args.getArgsWebDriver().getDriverName().toUpperCase());
        return switch (type) {
            case MSEDGE -> new MSEdgeBrowser(args).getDriver();
            case CHROME -> new ChromeBrowser(args).getDriver();
            default -> new FirefoxBrowser(args).getDriver();
        };
    }
}