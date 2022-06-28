package xyz.zimtools.zyod.od.identifiers;

import org.openqa.selenium.remote.RemoteWebDriver;

public final class WatchlistOnFireID extends ODIdentifier {
    private static final String TITLE = "watchlist on fire";
    private static final String LINK = "pmny.in";

    public WatchlistOnFireID(RemoteWebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isOD() {
        return this.header() || this.externalLink();
    }

    /**
     * Check header for id
     */
    private boolean header() {
        return this.findHasText("a.navbar-brand", TITLE, false);
    }

    /**
     * Check external footer link for id
     */
    private boolean externalLink() {
        return this.attributeCheck("footer p:nth-child(2) a[href]", "href", LINK);
    }
}