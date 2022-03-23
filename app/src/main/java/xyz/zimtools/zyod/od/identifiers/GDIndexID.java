package xyz.zimtools.zyod.od.identifiers;

import org.openqa.selenium.remote.RemoteWebDriver;

public final class GDIndexID extends ODID {
    private static final String TITLE = "GDIndex";
    private static final String REPO = "maple3142/GDIndex";

    public GDIndexID(RemoteWebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isOD() {
        return this.title() || this.header() || this.repoLink() || this.scriptTag() || this.linkTag();
    }

    /**
     * Look at {@code <title>} for id.
     *
     * @return true if id is found; false otherwise
     */
    private boolean title() {
        return this.findTextCheck("title", TITLE);
    }

    /**
     * Look at header for id
     */
    private boolean header() {
        return this.findTextCheck("div.v-toolbar__title span", TITLE);
    }

    /**
     * Look at header for link to repository
     */
    private boolean repoLink() {
        return this.attributeCheck("div.v-toolbar__items a[href]", "href", REPO);
    }

    /**
     * Look for id in {@code <script>}.
     */
    private boolean scriptTag() {
        return this.attributeCheck("script[src*=gdindex]", "src", TITLE.toLowerCase());
    }

    /**
     * Look for id in {@code <link>}.
     */
    private boolean linkTag() {
        return this.attributeCheck("link[href*=gdindex]", "href", TITLE.toLowerCase());
    }
}