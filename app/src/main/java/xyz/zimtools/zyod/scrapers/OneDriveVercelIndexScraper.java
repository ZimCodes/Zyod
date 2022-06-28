package xyz.zimtools.zyod.scrapers;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import xyz.zimtools.zyod.args.Args;
import xyz.zimtools.zyod.assets.Directory;
import xyz.zimtools.zyod.assets.info.NavInfo;
import xyz.zimtools.zyod.scrapers.filters.ScrapeFilter;

public class OneDriveVercelIndexScraper extends AttributeScraper {

    public OneDriveVercelIndexScraper(RemoteWebDriver driver, Args args, NavInfo navInfo, ScrapeFilter filter) {
        super(driver, args, navInfo, filter);
    }

    public OneDriveVercelIndexScraper(RemoteWebDriver driver, Args args, NavInfo navInfo) {
        super(driver, args, navInfo);
    }

    /**
     * Join specified attribute item found in {@link WebElement} to the current URL.
     *
     * @param element   scraped WebElement
     * @param parentDir the current parent {@link Directory}
     */
    @Override
    protected void joinLoop(WebElement element, Directory parentDir) {
        String fileAttr = element.getAttribute(this.navInfo.getCssAttr());
        String textContent = element.getText().strip();
        this.joinLinks(textContent + fileAttr, parentDir);
    }
}