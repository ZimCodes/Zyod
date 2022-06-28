package xyz.zimtools.zyod.od.identifiers;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import xyz.zimtools.zyod.support.NavSupport;

import java.util.List;

public final class GOIndexID extends ODIdentifier {

    public GOIndexID(RemoteWebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isOD() {
        return this.totalFiles() || this.footerLink() || this.scriptTags() || this.linkTags() || this.olderVersion();
    }

    /**
     * The footer divider containing the total number of files present.
     */
    private boolean totalFiles() {
        String[] langCSS = {"Total", "\u5171"};
        for (String css : langCSS) {
            String path = String.format("div.is-divider[data-content~=%s]", css);
            List<WebElement> elements = NavSupport.getIDElements(this.driver, path);
            if (!elements.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Footer links to author resources.
     */
    private boolean footerLink() {
        return this.exists("footer .tag[href]");
    }

    /**
     * {@code <script>} for id
     */
    private boolean scriptTags() {
        return this.exists("script[src*=goindex]");
    }

    /**
     * {#code <link>} for id
     */
    private boolean linkTags() {
        return this.exists("link[src*=goindex]");
    }

    /**
     * Identification for older version of this OD type.
     */
    private boolean olderVersion() {
        return this.attributeCheck("form#search_bar_form[action]", "action", "/0:search");
    }
}