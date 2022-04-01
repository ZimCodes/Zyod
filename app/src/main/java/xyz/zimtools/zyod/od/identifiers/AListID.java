package xyz.zimtools.zyod.od.identifiers;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import xyz.zimtools.zyod.support.NavSupport;

import java.util.List;
import java.util.Optional;

public final class AListID extends ODIdentifier {
    private final static String POWERED_TAG = "Powered by Alist";
    private final static String MANAGE_TAG = "Manage";
    private final static String TITLE_A = "Alist";
    private final static String TITLE_B = "alist-web";

    public AListID(RemoteWebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isOD() {
        return this.title() || this.footer() || this.qrCode() || this.imageLogo() || this.noscript()
                || this.linkPreloadTag() || this.toolbarIcons() || this.searchbar();
    }

    /**
     * Find the id in the footer
     */
    private boolean footer() {
        Optional<WebElement> element = NavSupport.getIDElement(this.driver, "div.footer div " +
                "a:first-child");
        boolean hasPoweredTag = this.textCheck(element, POWERED_TAG);
        if (!hasPoweredTag) {
            return false;
        }
        element = NavSupport.getIDElement(this.driver, "div.chakra-stack div span + a");
        return this.textCheck(element, MANAGE_TAG);
    }

    /**
     * Search for iconic toolbar
     */
    private boolean toolbarIcons() {
        List<WebElement> elements = NavSupport.getIDElements(this.driver, "div.overlay div" +
                ".chakra-stack span");
        if (elements.isEmpty()) {
            return false;
        }
        return elements.size() == 3;
    }

    /**
     * Find id in {@code <title>}.
     */
    private boolean title() {
        return this.findHasText("title", TITLE_A);
    }

    /**
     * Check preload {@code <link>} for id.
     */
    private boolean linkPreloadTag() {
        List<WebElement> elements = NavSupport.getIDElements(this.driver, "link[rel=modulepreload" +
                "][href]");
        if (elements.isEmpty()) {
            return false;
        }
        for (WebElement el : elements) {
            String attribute = el.getAttribute("href");
            boolean hasID = attribute.contains(TITLE_B);
            if (hasID) {
                return true;
            }
        }
        return false;
    }

    /**
     * Search for the QR code button
     */
    private boolean qrCode() {
        return this.exists("button.qrcode");
    }

    /**
     * Look for id in search bar placeholder
     */
    private boolean searchbar() {
        return this.attributeCheck("input.ant-input[placeholder]", "placeholder",
                "\u641C\u7D22\u6587\u4EF6(\u5939)");
    }

    /**
     * Look inside {@code <noscript>} for id
     */
    private boolean noscript() {
        return this.findHasText("noscript", TITLE_B);
    }

    /**
     * Search image logo for id
     */
    private boolean imageLogo() {
        return this.attributeCheck("img#logo[alt]", "alt", "AList");
    }
}