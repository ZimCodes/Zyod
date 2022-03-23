package xyz.zimtools.zyod.od.identifiers;

import org.openqa.selenium.remote.RemoteWebDriver;

public final class OneDriveVercelIndexID extends ODID {
    private static final String POWERED_TAG = "spencerwooo/onedrive-vercel-index";

    public OneDriveVercelIndexID(RemoteWebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isOD() {
        return this.footer() || this.metaTag();
    }

    /**
     * Check footer for tagline
     */
    private boolean footer() {
        return this.attributeCheck("main + div a[href]", "href", POWERED_TAG);
    }

    /**
     * Search {@code <meta>} for id
     */
    private boolean metaTag() {
        return this.exists("meta[content='OneDrive Vercel Index']");
    }
}