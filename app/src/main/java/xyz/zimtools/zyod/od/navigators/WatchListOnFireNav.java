package xyz.zimtools.zyod.od.navigators;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import xyz.zimtools.zyod.args.Args;
import xyz.zimtools.zyod.assets.info.DownloadInfo;
import xyz.zimtools.zyod.assets.info.NavInfo;
import xyz.zimtools.zyod.download.Downloader;
import xyz.zimtools.zyod.od.ODType;
import xyz.zimtools.zyod.scrapers.AttributeScraper;
import xyz.zimtools.zyod.scrapers.filters.GenericScrapeFilter;
import xyz.zimtools.zyod.support.InteractSupport;
import xyz.zimtools.zyod.support.NavSupport;

import java.util.Optional;

public class WatchListOnFireNav extends ODNavigator {

    public WatchListOnFireNav(RemoteWebDriver driver, Args args) {
        super(ODType.WATCHLIST_ON_FIRE, driver, args);
    }

    /**
     * Setup Scraper dependencies
     */
    @Override
    protected void setUpScraper(NavInfo navInfo, String navType) {
        this.scraper = new AttributeScraper(this.driver, this.args, navInfo,
                new GenericScrapeFilter());

    }

    /**
     * Setup {@link Downloader} dependencies.
     */
    @Override
    protected void setUpDownloader(NavInfo navInfo, String navType) {
        DownloadInfo downloadInfo = DOWNLOAD_INFO_PARSER.getInfo(this.id.name(),
                NavType.WatchList_On_Fire.MAIN.name());
        downloadInfo.setExtraTasks(this::mainTask);
        this.downloader = new Downloader(this.driver, this.args, navInfo, downloadInfo);
    }

    /**
     * Prepare a list of different navigational instructions for an OD
     *
     * @return list of different navigational variation for the OD; Otherwise an empty list;
     */
    @Override
    protected String[] prepareNavInfoTypes() {
        return new String[]{NavType.WatchList_On_Fire.MAIN.name()};
    }

    /**
     * Extra downloading task
     *
     * @param driver {@link RemoteWebDriver}
     * @param dlInfo {@link DownloadInfo}
     */
    private void mainTask(RemoteWebDriver driver, DownloadInfo dlInfo) {
        this.waiting();
        Optional<WebElement> elementOpt = NavSupport.getElement(driver,
                dlInfo.getCssDownloadTask());
        if (elementOpt.isPresent()) {
            WebElement element = elementOpt.get();
            InteractSupport.scrollToElement(driver, element);
            this.waiting();
            element.click();
            driver.navigate().back();
        }
    }
}