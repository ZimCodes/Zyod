package xyz.zimtools.zyod.args.validators.choice;

import xyz.zimtools.zyod.browsers.BrowserType;

public class WebDriverChoiceValidator extends ChoiceValidator {

    @Override
    void initChoices() {
        this.choices = new String[]{BrowserType.CHROME.toString(), BrowserType.FIREFOX.toString(),
                BrowserType.MSEDGE.toString()};
    }
}