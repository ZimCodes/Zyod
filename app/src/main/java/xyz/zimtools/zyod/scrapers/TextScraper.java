package xyz.zimtools.zyod.scrapers;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import xyz.zimtools.zyod.args.Args;
import xyz.zimtools.zyod.assets.Directory;
import xyz.zimtools.zyod.assets.info.NavInfo;
import xyz.zimtools.zyod.scrapers.filters.ScrapeFilter;
import xyz.zimtools.zyod.support.NavSupport;

import java.util.List;

/**
 * Scrape ODs using the {@link WebElement}'s text content.
 */
public class TextScraper extends AttributeScraper {

    public TextScraper(RemoteWebDriver driver, Args args, NavInfo navInfo, ScrapeFilter filter) {
        super(driver, args, navInfo, filter);
    }

    public TextScraper(RemoteWebDriver driver, Args args, NavInfo navInfo) {
        super(driver, args, navInfo);
    }

    /**
     * Scrape for {@link WebElement}s using CSS name
     *
     * @return list of scraped WebElements
     */
    @Override
    public List<WebElement> scrapeItems() {
        this.pause();
        return NavSupport.getElements(this.driver, this.navInfo.getCssFileName());
    }

    /**
     * Join text content found in {@link WebElement} to the current URL.
     *
     * @param element   scraped WebElement
     * @param parentDir the current parent {@link Directory}
     */
    @Override
    protected void joinLoop(WebElement element, Directory parentDir) {
        String text = element.getText().strip();
        this.joinLinks(text, parentDir);
    }
}