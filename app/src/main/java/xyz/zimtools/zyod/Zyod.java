package xyz.zimtools.zyod;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.remote.RemoteWebDriver;
import xyz.zimtools.zyod.args.Args;
import xyz.zimtools.zyod.assets.Directory;
import xyz.zimtools.zyod.assets.ODUrl;
import xyz.zimtools.zyod.browsers.Browser;
import xyz.zimtools.zyod.browsers.BrowserFactory;
import xyz.zimtools.zyod.od.navigators.Navigator;
import xyz.zimtools.zyod.od.navigators.NavigatorFactory;

import java.util.*;

/**
 * Zyod Application
 */
class Zyod {
    private final Args parsedArgs;
    private RemoteWebDriver driver;
    // WebDriver for identifying the OD type
    private RemoteWebDriver idDriver;
    private Navigator navigator;
    // All scraped files
    private final Set<ODUrl> fileSet;
    /// Total directories traversed
    private long dirCount;

    public Zyod(String[] args) {
        this.parsedArgs = new Args(args);
        this.fileSet = new HashSet<>();
    }

    /**
     * Start the application
     */
    public void start() {
        if (this.parsedArgs.getArgsMain().isVersion()) {
            Talker.divider();
            Talker.info("zyod", AppConfig.getAppVersion(), true);
            Talker.divider();
            return;
        }
        this.init();
        List<String> urls = this.parsedArgs.getArgsMain().getUrls();
        for (String url : urls) {
            this.goToPage(this.idDriver, url);
            this.initNavigation();
            this.goToPage(this.driver, url);
            this.useNavigator(url);
        }
        this.recordToFile();
        this.shutdown();
    }

    /**
     * Initializes important constants
     */
    private void init() {
        Talker.loading("Initializing constants", false);
        Browser browser = BrowserFactory.getBrowser(this.parsedArgs);
        this.idDriver = browser.getIDDriver();
        this.driver = browser.getDriver();
        if (this.parsedArgs.getArgsMain().isVerbose()) {
            Talker.complete("Constants are loaded", false);
        }
    }

    /**
     * Navigate to another webpage
     *
     * @param url the webpage to navigate to
     */
    private void goToPage(RemoteWebDriver remoteWebDriver, String url) {
        Talker.loading(String.format("Navigating to %s ", url), false);
        try {
            remoteWebDriver.get(url);
            if (this.parsedArgs.getArgsMisc().isInitRefresh()) {
                if (this.parsedArgs.getArgsMain().isVerbose()) {
                    Talker.loading("Refreshing Page", false);
                }
                AppConfig.sleep(6000L);
                remoteWebDriver.navigate().refresh();
            }
        } catch (TimeoutException e) {
            remoteWebDriver.navigate().refresh();
        }

        long initPageWait = this.parsedArgs.getArgsMisc().getInitPageWait();
        if (initPageWait > 0L) {
            AppConfig.sleep(initPageWait);
        }
        Talker.complete("Navigation Complete", true);
    }

    /**
     * Initializes non-constant navigational constants
     */
    private void initNavigation() {
        Talker.loading("Initializing Navigator", false);
        this.navigator = NavigatorFactory.getNavigator(this.idDriver, this.driver, this.parsedArgs);
        if (this.parsedArgs.getArgsMain().isVerbose()) {
            Talker.complete("Navigator Finalized", false);
        }
        this.idDriver.quit();
        Talker.arrowHeaderInfo("Navigation Method", this.navigator.getId(), true);
    }

    /**
     * Use the {@link Navigator} to scrape & download resources recursively
     *
     * @param url The current starting webpage
     */
    private void useNavigator(String url) {
        if (this.parsedArgs.getArgsDownload().isDownloading()) {
            Talker.loading("Begin Scrape & Download Process", false);
        } else {
            Talker.loading("Begin Scrape Process", false);
        }
        Deque<Directory> dirsToNavigate = new LinkedList<>(List.of(new Directory(0,
                new ODUrl(url))));
        while (!dirsToNavigate.isEmpty()) {
            Directory curDir = dirsToNavigate.pop();
            Talker.currentDirectory(curDir, this.parsedArgs.getArgsMain().isVerbose());
            this.navigator.navigate(curDir);
            if (this.parsedArgs.getArgsMain().isVerbose()){
                Talker.fileStats(this.navigator.getDirResults(), this.navigator.getFileResults());
            }
            this.dirCount += this.navigator.getDirResults().size();
            this.fileSet.addAll(this.navigator.getFileResults());
            dirsToNavigate.addAll(this.navigator.getDirResults());
        }

        if (this.parsedArgs.getArgsDownload().isDownloading()) {
            Talker.complete("Finished Scrape & Download Process", true);
        } else {
            Talker.complete("Finished Scrape Process", true);
        }
    }

    /**
     * Record all file URLs to a file
     */
    private void recordToFile() {
        if (this.parsedArgs.getArgsRecord().isNotRecording() || this.fileSet.isEmpty()) {
            return;
        }
        Talker.loading("Begin Recording Process", false);
        Writer.recordFiles(this.parsedArgs.getArgsRecord().getOutputFile(), this.fileSet.stream().toList());
        Talker.complete("Finished Recording Process", true);
    }

    /**
     * Shutdown Zyod application
     */
    private void shutdown() {
        this.displayStats();
        Talker.header("All Tasks Completed", false);
        if (this.parsedArgs.getArgsWebDriver().isHeadless()) {
            this.headlessShutdown();
        } else {
            this.headShutdown();
        }
        this.driver.quit();
    }

    /**
     * Read navigational stats
     */
    private void displayStats() {
        if (this.parsedArgs.getArgsMain().isVerbose()) {
            Talker.header("Grand Total Summary", false);
            Talker.arrowInfo("Traversed Directories", String.format("%d", this.dirCount), false);
            Talker.arrowInfo("Scanned Files", String.format("%d", this.fileSet.size()), false);
            Talker.divider();
        }
    }

    /**
     * Headless Shutdown of Zyod
     */
    private void headlessShutdown() {
        Talker.loading("Zyod is shutting down", false);
    }

    /**
     * Non-Headless mode shutdown.
     *
     * <p>
     * When browser is closed, Zyod will stop running. The browser will have to be closed
     * manually in order to provide unlimited time to download resources.
     * </p>
     */
    private void headShutdown() {
        Talker.loading("Please close browser to end process", true);
        boolean isBrowserRunning = true;
        while (isBrowserRunning) {
            isBrowserRunning = !this.driver.findElements(By.cssSelector("html")).isEmpty();
            AppConfig.sleep(4500L);
        }
    }
}