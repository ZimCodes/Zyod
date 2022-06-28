package xyz.zimtools.zyod.od.navigators;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import xyz.zimtools.zyod.args.Args;
import xyz.zimtools.zyod.assets.Directory;
import xyz.zimtools.zyod.assets.ODUrl;
import xyz.zimtools.zyod.assets.info.DownloadInfo;
import xyz.zimtools.zyod.assets.info.NavInfo;
import xyz.zimtools.zyod.download.Downloader;
import xyz.zimtools.zyod.download.filters.FileDLFilter;
import xyz.zimtools.zyod.od.ODType;
import xyz.zimtools.zyod.scrapers.AttributeScraper;
import xyz.zimtools.zyod.scrapers.TextScraper;
import xyz.zimtools.zyod.support.NavSupport;

import java.util.Map;
import java.util.Optional;


public class AListNav extends ODNavigator {

    public AListNav(RemoteWebDriver driver, Args args) {
        super(ODType.ALIST, driver, args);
    }

    /**
     * Setup Scraper dependencies
     */
    @Override
    protected void setUpScraper(NavInfo navInfo, String navType) {
        if (navType.equals(NavType.AList.WEB.name())) {
            this.scraper = new TextScraper(this.driver, this.args, navInfo);
        } else {
            this.scraper = new AttributeScraper(this.driver, this.args, navInfo);
        }
    }

    /**
     * Prepare a list of different navigational instructions for an OD
     *
     * @return list of different navigational variation for the OD; Otherwise an empty list;
     */
    @Override
    protected String[] prepareNavInfoTypes() {
        return new String[]{NavType.AList.ORIGINAL.name(), NavType.AList.WEB.name()};
    }

    /**
     * Setup {@link Downloader} dependencies.
     */
    @Override
    protected void setUpDownloader(NavInfo navInfo, String navType) {
        DownloadInfo downloadInfo = DOWNLOAD_INFO_PARSER.getInfo(this.id.name(), navType);
        downloadInfo.setExtraTasks(this::originalTasks);
        this.downloader = new Downloader(driver, this.args, navInfo, downloadInfo, new FileDLFilter());
    }

    @Override
    protected Map<String, Object> goToDirectory(Directory directory) {
        if (!this.driver.getCurrentUrl().equals(directory.toString())) {
            long totalPaths = directory.totalFolders();
            if (totalPaths <= 1) {
                this.driver.get(directory.toString());
                directory = new Directory(directory.getDepthLevel(),
                        new ODUrl(this.driver.getCurrentUrl()));
            } else if (!directory.toString().isEmpty()) {
                if (!directory.toString().endsWith("/")) {
                    this.driver.get(directory.toString());
                } else {
                    this.driver.get(directory.toString().substring(0,
                            directory.toString().length() - 1));
                }
            }
        }
        return Map.of(NOT_LOGIN_KEY, true, DIRECTORY_KEY, directory);
    }

    /**
     * Extra tasks for Original variant of AList
     *
     * @param driver {@link RemoteWebDriver}
     */
    private void originalTasks(RemoteWebDriver driver, DownloadInfo dlInfo) {
        this.waiting();
        Optional<WebElement> element = NavSupport.getElement(this.driver,
                dlInfo.getCssDownloadTask());
        if (element.isPresent()) {
            element.get().click();
            driver.navigate().back();
        }
    }
}