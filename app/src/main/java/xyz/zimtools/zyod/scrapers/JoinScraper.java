package xyz.zimtools.zyod.scrapers;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import xyz.zimtools.zyod.AppConfig;
import xyz.zimtools.zyod.args.Args;
import xyz.zimtools.zyod.assets.Directory;
import xyz.zimtools.zyod.assets.ODUrl;
import xyz.zimtools.zyod.assets.info.NavInfo;
import xyz.zimtools.zyod.scrapers.filters.ScrapeFilter;
import xyz.zimtools.zyod.support.NavSupport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Scrape ODs using the {@link WebElement}'s existing attributes.
 */
public class JoinScraper implements ODScraper {
    protected final RemoteWebDriver driver;
    protected final Args args;
    protected final ScrapeFilter filter;
    protected final NavInfo navInfo;
    protected List<ODUrl> files;
    protected List<Directory> dirs;

    public JoinScraper(RemoteWebDriver driver, Args args, NavInfo navInfo, ScrapeFilter filter) {
        this.driver = driver;
        this.args = args;
        this.navInfo = navInfo;
        this.filter = filter;
        this.files = new ArrayList<>(); // Holds all scraped file links
        this.dirs = new ArrayList<>(); // Holds all scraped directories
    }

    public JoinScraper(RemoteWebDriver driver, Args args, NavInfo navInfo) {
        this(driver, args, navInfo, null);
    }

    @Override
    public NavInfo getNavInfo() {
        return this.navInfo;
    }

    @Override
    public List<ODUrl> getFiles() {
        return this.files;
    }

    @Override
    public List<Directory> getDirs() {
        return this.dirs;
    }

    /**
     * Begins scraping the current OD
     *
     * @param elements  list of scraped {@link WebElement}s
     * @param parentDir the current parent {@link Directory} to scrape
     * @return list of scraped WebElements
     */
    @Override
    public List<WebElement> scrape(List<WebElement> elements, Directory parentDir) {
        if (elements.isEmpty()) {
            elements = this.scrapeItems();
            elements = this.refresh(elements);
        }
        this.storeLinks(elements, parentDir);
        return elements;
    }

    /**
     * Scrape for {@link WebElement}s using CSS file selector
     *
     * @return list of scraped WebElements
     */
    @Override
    public List<WebElement> scrapeItems() {
        long waitTime = this.args.getArgsScraper().getWait();
        if (waitTime > 0) {
            AppConfig.sleep(waitTime);
        }
        return NavSupport.getElements(this.driver, this.navInfo.getCssFileSelector());
    }


    /**
     * Appends the link found in {@link WebElement}s onto the current URL and save them.
     *
     * @param elements  list of scraped WebElements
     * @param parentDir current parent directory
     */
    private void storeLinks(List<WebElement> elements, Directory parentDir) {
        if (elements.isEmpty()) {
            return;
        }
        for (WebElement el : elements) {
            this.joinLoop(el, parentDir);
        }
    }

    /**
     * Join specified attribute item found in {@link WebElement} to the current URL.
     *
     * @param element   scraped WebElement
     * @param parentDir the current parent {@link Directory}
     */
    protected void joinLoop(WebElement element, Directory parentDir) {
        String fileLink = element.getAttribute(this.navInfo.getCssAttr());
        this.joinLinks(fileLink, parentDir);
    }

    /**
     * Filter & join links together to make a new URL.
     *
     * @param link      new link to join together
     * @param parentDir current parent {@link Directory}
     */
    protected void joinLinks(String link, Directory parentDir) {
        if (this.isHome(link) || this.isJSVoid(link)) {
            return;
        }
        // NOTE: Files with extension length > 7 & < 3 are considered directories.
        link = this.applyFilter(link);
        if (ODUrl.isFile(link)) {
            ODUrl joinedURL = ODUrl.joiner(parentDir.toString(), link);
            this.files.add(joinedURL);
        } else {
            int newDepthLevel = parentDir.getDepthLevel() + 1;
            ODUrl dirURL = ODUrl.joiner(parentDir.toString(), link);
            dirURL.dirTransform();
            Directory newDir = new Directory(newDepthLevel, dirURL);
            if (newDepthLevel < this.args.getArgsNavigator().getDepth()) {
                this.dirs.add(newDir);
            }
        }
    }

    /**
     * Refresh the page.
     * <p>
     * Current page will refresh in an attempt to retrieve {@link WebElement}s.
     * </p>
     *
     * @return list of WebElements on current page.
     */
    protected List<WebElement> refresh(List<WebElement> elements) {
        if (elements.isEmpty() && this.args.getArgsMisc().isRefreshing()) {
            this.driver.navigate().refresh();
            elements = this.scrapeItems();
        }
        return elements;
    }

    /**
     * Check if text points to a home directory
     * <p>
     * Determine if specified text leads to a previous parent directory, homepage, or front
     * page.
     * </p>
     *
     * @param text the text to check
     * @return true if text leads to a home directory or previous parent directory; false
     * otherwise.
     */
    private boolean isHome(String text) {
        String[] homeList = {"/", ".", "..", "../", "./"};
        return Arrays.asList(homeList).contains(text);
    }

    /**
     * Check if {@code javascript:} is present.
     * <p>
     * {@code javascript:} usually indicate the execution of a JavaScript code, which is not
     * needed when attempting to retrieve OD resources.
     * </p>
     *
     * @param text the text to check.
     * @return true if {@code javascript:} is present; otherwise false.
     */
    private boolean isJSVoid(String text) {
        return text.contains("javascript:");
    }

    /**
     * Apply Scraper filter.
     *
     * @param text the text to apply the filter to
     * @return the filter text
     */
    @Override
    public String applyFilter(String text) {
        return this.filter != null ? this.filter.apply(text) : text;
    }

    /**
     * Reset records of all files and directories scraped
     */
    @Override
    public void reset() {
        this.files.clear();
        this.dirs.clear();
    }
}