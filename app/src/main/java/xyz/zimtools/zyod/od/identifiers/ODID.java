package xyz.zimtools.zyod.od.identifiers;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import xyz.zimtools.zyod.support.NavSupport;

import java.util.List;
import java.util.Optional;

abstract class ODID implements ODIdentifier {
    protected final RemoteWebDriver driver;

    public ODID(RemoteWebDriver driver) {
        this.driver = driver;
    }

    /**
     * Determines if current OD is this OD
     */
    @Override
    public abstract boolean isOD();

    /**
     * Check the text content of an element for a match
     *
     * @param element         {@link WebElement} to check
     * @param matchText       the text to match.
     * @param caseSensitivity case-sensitive text matching
     * @return true if text from the element matches; false otherwise
     */
    protected boolean textCheck(Optional<WebElement> element, String matchText,
                                boolean caseSensitivity) {
        boolean isMatch = false;
        if (element.isPresent()) {
            WebElement el = element.get();
            if (caseSensitivity) {
                isMatch = matchText.equals(el.getText().strip());
            } else {
                isMatch = matchText.equalsIgnoreCase(el.getText());
            }
        }
        return isMatch;
    }

    protected boolean textCheck(Optional<WebElement> element, String matchText) {
        return this.textCheck(element, matchText, true);
    }

    /**
     * Finds a {@link WebElement} and check its text content.
     *
     * @param cssSelect       the CSS path to the element
     * @param matchText       the text to match
     * @param caseSensitivity case-sensitive text matching
     */
    protected boolean findTextCheck(String cssSelect, String matchText, boolean caseSensitivity) {
        Optional<WebElement> element = NavSupport.getElement(this.driver, cssSelect);
        return this.textCheck(element, matchText, caseSensitivity);
    }

    protected boolean findTextCheck(String cssSelect, String matchText) {
        return this.findTextCheck(cssSelect, matchText, true);
    }

    /**
     * Checks if a phrase exists inside an element's text content
     *
     * @param element         {@link WebElement} to check
     * @param matchText       the text to match
     * @param caseSensitivity case-sensitive text matching
     * @return true if text from the element matches; false otherwise
     */
    protected boolean hasText(Optional<WebElement> element, String matchText,
                              boolean caseSensitivity) {
        boolean isMatch = false;
        if (element.isPresent()) {
            WebElement el = element.get();
            if (caseSensitivity) {
                isMatch = el.getText().strip().contains(matchText);
            } else {
                isMatch = el.getText().strip().toLowerCase().contains(matchText.toLowerCase());
            }
        }
        return isMatch;
    }

    protected boolean hasText(Optional<WebElement> element, String matchText) {
        return this.hasText(element, matchText, true);
    }

    /**
     * Finds a {@link WebElement} and check if element text content contains a phrase
     *
     * @param cssSelect the CSS path to the WebElement
     * @param matchText the text to match
     * @param caseSensitivity case-sensitive text matching
     * @return true if phrase does exist inside web element; false otherwise
     */
    protected boolean findHasText(String cssSelect, String matchText, boolean caseSensitivity) {
        Optional<WebElement> element = NavSupport.getElement(this.driver, cssSelect);
        return this.hasText(element, matchText, caseSensitivity);
    }

    protected boolean findHasText(String cssSelect, String matchText) {
        return this.findHasText(cssSelect, matchText, true);
    }


    /**
     * Check {@link WebElement}'s attribute for a match.
     *
     * @param cssSelect CSS path to element
     * @param attribute the attribute to retrieve from cssSelect
     * @param matchText text to match against
     * @return True if attribute matches text; false otherwise
     */
    protected boolean attributeCheck(String cssSelect, String attribute, String matchText) {
        Optional<WebElement> element = NavSupport.getElement(this.driver, cssSelect);
        if (element.isEmpty()) {
            return false;
        }
        String attributeText = element.get().getAttribute(attribute);
        return attributeText.contains(matchText);
    }

    /**
     * Check if {@link WebElement} is exists
     *
     * @param cssSelect the webelement to check
     */
    protected boolean exists(String cssSelect) {
        List<WebElement> elements = NavSupport.getElements(this.driver, cssSelect);
        return !elements.isEmpty();
    }
}