package xyz.zimtools.zyod.od.identifiers;

import org.openqa.selenium.remote.RemoteWebDriver;

public final class ShareListID extends ODIdentifier {
    private static final String REPO = "reruin/sharelist";
    private static final String MANAGE_EN = "Manage";
    private static final String MANAGE_CH = "\u7BA1\u7406";
    private static final String TITLE = "ShareList";


    public ShareListID(RemoteWebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isOD() {
        return this.title() || this.header() || this.footer();
    }

    /**
     * Find id in {@code <title>}
     */
    private boolean title() {
        return this.findTextCheck("title", TITLE);
    }

    /**
     * Find id in {@code <footer>}
     */
    private boolean footer() {
        boolean hasRepo = this.attributeCheck("footer p a:first-child[href]", "href", REPO);
        String manageCSS = "footer p a:nth-child(2)[href]";
        boolean hasManage = this.findTextCheck(manageCSS, MANAGE_EN);
        if (!hasManage) {
            hasManage = this.findTextCheck(manageCSS, MANAGE_CH);
        }
        return hasManage && hasRepo;
    }

    /**
     * Find id through the header title
     */
    private boolean header() {
        boolean hasTitle = this.findTextCheck("div.wrap > a", TITLE, false);
        if (!hasTitle) {
            hasTitle = this.findTextCheck("div.drive-header__name", TITLE, false);
        }
        return hasTitle;
    }
}