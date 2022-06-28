package xyz.zimtools.zyod.od.navigators;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import xyz.zimtools.zyod.AppConfig;
import xyz.zimtools.zyod.args.Args;
import xyz.zimtools.zyod.assets.Directory;
import xyz.zimtools.zyod.assets.ODUrl;
import xyz.zimtools.zyod.assets.info.DownloadInfo;
import xyz.zimtools.zyod.assets.info.DownloadInfoParser;
import xyz.zimtools.zyod.assets.info.NavInfo;
import xyz.zimtools.zyod.assets.info.NavInfoParser;
import xyz.zimtools.zyod.download.Downloader;
import xyz.zimtools.zyod.od.ODType;
import xyz.zimtools.zyod.scrapers.Scraper;
import xyz.zimtools.zyod.support.InteractSupport;
import xyz.zimtools.zyod.support.NavSupport;

import java.util.List;
import java.util.Map;

/**
 * Navigate an OD using NavInfo CSS selector
 */
abstract class ODNavigator implements Navigator {
    protected static final NavInfoParser NAV_INFO_PARSER;
    protected static final DownloadInfoParser DOWNLOAD_INFO_PARSER;
    protected static final String NOT_LOGIN_KEY = "not_login";
    protected static final String DIRECTORY_KEY = "directory";
    protected final ODType id;
    protected final RemoteWebDriver driver;
    protected final Args args;
    protected Scraper scraper;
    protected Downloader downloader;

    static {
        NAV_INFO_PARSER = new NavInfoParser();
        DOWNLOAD_INFO_PARSER = new DownloadInfoParser();
    }

    public ODNavigator(ODType id, RemoteWebDriver driver, Args args) {
        this.id = id;
        this.driver = driver;
        this.args = args;
    }

    public String getId() {
        return this.id.name();
    }

    /**
     * Begin navigating an OD.
     *
     * @param directory {@link Directory} to navigate to
     */
    @Override
    public void navigate(Directory directory) {
        List<WebElement> elements = this.setUpNavigation(directory);
        if (!elements.isEmpty() && this.args.getArgsDownload().isDownloading()) {
            this.download();
        }
    }

    /**
     * Download contents from current directory
     */
    protected void download() {
        this.downloader.multiDownload();
    }

    /**
     * Setup navigation dependencies.
     *
     * @param parentDir current parent {@link Directory}
     * @return list of navigation elements
     */
    private List<WebElement> setUpNavigation(Directory parentDir) {
        if (this.scraper != null) {
            this.scraper.reset();
        }
        Map<String, Object> dirInfo = this.goToDirectory(parentDir);
        Boolean isNotLoginPage = (Boolean) dirInfo.get(NOT_LOGIN_KEY);
        parentDir = (Directory) dirInfo.get(DIRECTORY_KEY);
        if (!isNotLoginPage) {
            return List.of();
        }
        List<WebElement> elements = List.of();
        if (this.scraper == null) {
            elements = this.setUpDependencies();
        } else if (this.args.getArgsInteractive().isScrolling()) {
            elements = this.scrollToBottom(elements);
        }

        if (this.scraper != null) {
            elements = this.scraper.scrape(elements, parentDir);
        }
        return elements;
    }

    /**
     * Setup navigator components' dependencies.
     * <p>
     * The components of the navigator will be initialized. These components includes the
     * {@link Scraper}, {@link Downloader}, & {@link xyz.zimtools.zyod.assets.info.NavInfo}
     * </p>
     */
    private List<WebElement> setUpDependencies() {
        String[] navInfoTypes = this.prepareNavInfoTypes();
        for (String navType : navInfoTypes) {
            NavInfo navInfo = NAV_INFO_PARSER.getInfo(this.id.name(), navType);
            List<WebElement> elements = this.getNavInfoElements(navInfo);
            if (!elements.isEmpty()) {
                this.setUpScraper(navInfo, navType);
                this.setUpDownloader(navInfo, navType);
                if (this.args.getArgsInteractive().isScrolling()) {
                    elements = this.scrollToBottom(elements);
                }
                return elements;
            }
        }
        return List.of();
    }

    /**
     * Scroll until bottom of page is reached.
     *
     * @param prevElements previous list of {@link WebElement}s
     * @return list of WebElements found while scrolling.
     */
    private List<WebElement> scrollToBottom(List<WebElement> prevElements) {
        if (prevElements == null) {
            prevElements = List.of();
        }
        InteractSupport.globalScrollDown(this.driver);
        long scrollWait = this.args.getArgsInteractive().getScrollWait();
        if (scrollWait > 0) {
            AppConfig.sleep(scrollWait);
        }
        List<WebElement> elements = this.scraper.scrapeItems();
        if (elements.size() > prevElements.size()) {
            this.scrollToBottom(elements);
        }
        return elements;
    }

    /**
     * Setup Scraper dependencies
     **/
    protected abstract void setUpScraper(NavInfo navInfo, String navType);

    /**
     * Setup {@link Downloader} dependencies.
     */
    protected void setUpDownloader(NavInfo navInfo, String navType) {
        DownloadInfo downloadInfo = DOWNLOAD_INFO_PARSER.getInfo(this.id.name(), navType);
        this.downloader = new Downloader(this.driver, this.args, navInfo, downloadInfo);
    }

    /**
     * Retrieve {@link WebElement}s to determine if a navigational instruction is appropriate for
     * the OD.
     *
     * @param navInfo {@link NavInfo}
     * @return a list of WebElements or empty
     */
    protected List<WebElement> getNavInfoElements(NavInfo navInfo) {
        return NavSupport.getElements(this.driver, navInfo.getCssFileSelector());
    }


    /**
     * Prepare a list of different navigational instructions for an OD
     *
     * @return list of different navigational variation for the OD; Otherwise an empty list;
     */
    protected abstract String[] prepareNavInfoTypes();

    protected Map<String, Object> goToDirectory(Directory directory) {
        if (!this.driver.getCurrentUrl().equals(directory.toString())) {
            if (!directory.toString().isEmpty()) {
                this.driver.get(directory.toString());
            }
        }
        return Map.of(NOT_LOGIN_KEY, true, DIRECTORY_KEY, directory);
    }

    /**
     * Retrieve the Directories found in current page.
     *
     * @return list of directories
     * @see Scraper
     * @see Directory
     */
    @Override
    public List<Directory> getDirResults() {
        return this.scraper.getDirs();
    }

    /**
     * Retrieve files located on current page
     *
     * @return list of {@link ODUrl}
     * @see ODUrl
     * @see Scraper
     */
    @Override
    public List<ODUrl> getFileResults() {
        return this.scraper.getFiles();
    }

    protected void waiting() {
        long interactWait = this.args.getArgsInteractive().getInteractWait();
        if (interactWait > 0) {
            AppConfig.sleep(interactWait);
        }
    }
}