package xyz.zimtools.zyod.od.identifiers;

import org.openqa.selenium.remote.RemoteWebDriver;


public final class GONEListID extends ODIdentifier {
    private static final String TITLE = "GONEList";
    private static final String GLOBAL_OPTION = "\u5168\u5C40";
    private static final String OTHER_OPTION = "\u5F53\u524D";

    public GONEListID(RemoteWebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isOD() {
        return this.header() || this.footer() || this.searchOptions();
    }

    /**
     * Find the id in the footer.
     */
    private boolean footer() {
        return this.findTextCheck("#footer span a:first-child", TITLE);
    }

    /**
     * Find the id in the header
     */
    private boolean header() {
        return this.findTextCheck("div h1", TITLE);
    }

    /**
     * Check if search options exists
     */
    private boolean searchOptions() {
        if (!this.findTextCheck("div.search-container button:first-child span", GLOBAL_OPTION)) {
            return false;
        }
        return this.findTextCheck("div.search-container button:nth-child(2) span", OTHER_OPTION);
    }
}