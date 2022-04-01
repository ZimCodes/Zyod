package xyz.zimtools.zyod.od.identifiers;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import xyz.zimtools.zyod.support.NavSupport;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class FODIID extends ODIdentifier {
    private static final String PATH = "/fodi/";
    private static final Map<String, String> HEADER_MAP;

    static {
        HEADER_MAP = Map.of("ITEMS", "name", "TIME", "time", "SIZE", "size");
    }


    public FODIID(RemoteWebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isOD() {
        return this.scriptTags() || this.checkHeaders();
    }

    /**
     * Find id in {@code <script>}.
     *
     * @return true if OD contains {@code /fodi/} in {@code <script>}; false otherwise.
     */
    private boolean scriptTags() {
        List<WebElement> elements = NavSupport.getIDElements(this.driver, "script");
        for (WebElement el : elements) {
            if (el.getText().contains(PATH)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if headers are present.
     *
     * @return true if all headers exist; false otherwise.
     */
    private boolean checkHeaders() {
        for (Map.Entry<String, String> keySet : HEADER_MAP.entrySet()) {
            Optional<WebElement> element = NavSupport.getIDElement(this.driver, "div.right div" +
                    ".list-header div.file span." + keySet.getValue());
            if (!this.textCheck(element, keySet.getKey())) {
                return false;
            }
        }
        return true;
    }

}