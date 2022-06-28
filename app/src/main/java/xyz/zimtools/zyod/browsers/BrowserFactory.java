package xyz.zimtools.zyod.browsers;

import xyz.zimtools.zyod.args.Args;
/**
 * Creates all available drivers for use.
 * */
public final class BrowserFactory {
    public static Browser getBrowser(Args args) {
        BrowserType type =
                BrowserType.valueOf(args.getArgsWebDriver().getDriverName().toUpperCase());
        return switch (type) {
            case MSEDGE -> new MSEdgeBrowser(args);
            case CHROME -> new ChromeBrowser(args);
            default -> new FirefoxBrowser(args);
        };
    }
}