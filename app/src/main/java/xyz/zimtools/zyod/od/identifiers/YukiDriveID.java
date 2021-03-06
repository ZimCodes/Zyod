package xyz.zimtools.zyod.od.identifiers;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import xyz.zimtools.zyod.support.NavSupport;

import java.util.List;
import java.util.Optional;

public final class YukiDriveID extends ODIdentifier {
    private static final String TITLE = "Yuki Drive";
    private static final String FILE_SECT_A = "\u6587\u4EF6";
    private static final String FILE_SECT_B = "\u6587\u4EF6\u5939";
    private static final String SIDE_DRAWER = "\u9A71\u52A8\u5668";


    public YukiDriveID(RemoteWebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isOD() {
        return this.title() || this.noscript() || this.sideDrawer() || this.fileSections();
    }

    /**
     * Check {@code <title>} for id
     */
    private boolean title() {
        return this.findTextCheck("head title", TITLE);
    }

    /**
     * Look for the presence of file sections
     */
    private boolean fileSections() {
        Optional<WebElement> element = NavSupport.getIDElement(this.driver, "div.v-list " +
                "div:first-child div.v-subheader");
        boolean hasFileSection = this.textCheck(element, FILE_SECT_A);
        if (hasFileSection) {
            return true;
        }
        return this.textCheck(element, FILE_SECT_B);
    }

    /**
     * Check {@code <noscript>} for id
     */
    private boolean noscript() {
        List<WebElement> elements = NavSupport.getIDElements(this.driver, "noscript");
        for (WebElement el : elements) {
            boolean hasId = this.hasText(Optional.of(el), TITLE);
            if (hasId) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check the side drawer for id
     */
    private boolean sideDrawer() {
        return this.findTextCheck(
                "div.v-navigation-drawer__content " +
                        "div.v-list-group__header " +
                        "div.v-list-item__content " +
                        "div.v-list-item__title", SIDE_DRAWER);
    }


}