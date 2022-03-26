package xyz.zimtools.zyod.scrapers;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import xyz.zimtools.zyod.args.Args;
import xyz.zimtools.zyod.assets.info.NavInfo;
import xyz.zimtools.zyod.scrapers.filters.ScrapeFilter;
import xyz.zimtools.zyod.support.NavSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Scrape ODs using the {@link org.openqa.selenium.WebElement}'s text content while also
 * filtering out unwanted WebElements.
 */
public class SiftScraper extends TextScraper {
    private final List<WebElement> elsToKeep;

    public SiftScraper(RemoteWebDriver driver, Args args, NavInfo navInfo, ScrapeFilter filter) {
        super(driver, args, navInfo, filter);
        this.elsToKeep = new ArrayList<>();
    }

    public SiftScraper(RemoteWebDriver driver, Args args, NavInfo navInfo) {
        super(driver, args, navInfo);
        this.elsToKeep = new ArrayList<>();
    }

    /**
     * Scrape and filter out {@link WebElement}s.
     *
     * <p>
     * While scraping WebElements from an OD, some elements will be filter out during the
     * process.
     * <p/>
     *
     * <p>
     * The {@code NavInfo.cssFileSelector} will select all possible WebElements pointing to a
     * resource(file/folder). While looking through each of these WebElements, the {@code
     * NavInfo.cssRejectFilter} will be used to determine if an element should be filtered
     * out. If WebElement doesn't contain the {@code NavInfo.cssRejectFilter} path, the text
     * content of this WebElement, found using {NavInfo.cssFileName}, will be added to a list
     * of elements to keep.Otherwise it will be ignored.
     * </p>
     *
     * @return list of scraped WebElements
     */
    @Override
    public List<WebElement> scrapeItems() {
        this.pause();
        if (!this.elsToKeep.isEmpty()) {
            this.elsToKeep.clear();
        }
        List<WebElement> allEls;
        List<WebElement> elNames;

        allEls = NavSupport.getElements(this.driver, this.navInfo.getCssFileSelector());
        elNames = NavSupport.getElements(this.driver, this.navInfo.getCssFileName());

        for (int i = 0; i < allEls.size(); i++) {
            try {
                //NOTE: FindElement depends on implicit wait ('--element-wait'). Recommend
                // reducing implicit wait time to 0.
                allEls.get(i).findElement(By.cssSelector(this.navInfo.getCssRejectFilter()));
            } catch (NoSuchElementException e) {
                this.elsToKeep.add(elNames.get(i));
            }
        }
        return this.elsToKeep;
    }
}
