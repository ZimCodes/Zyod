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
import xyz.zimtools.zyod.support.NavSupport;

import java.util.Optional;

public class GoIndexNav extends ODNavigator {

    public GoIndexNav(RemoteWebDriver driver, Args args) {
        super(ODType.GOINDEX, driver, args);
    }

    /**
     * Setup Scraper dependencies
     */
    @Override
    protected void setUpScraper(NavInfo navInfo, String navType) {
        this.scraper = new AttributeScraper(this.driver, this.args, navInfo, new GenericScrapeFilter());
    }

    /**
     * Setup {@link Downloader} dependencies.
     */
    @Override
    protected void setUpDownloader(NavInfo navInfo, String navType) {
        DownloadInfo downloadInfo;
        if (navType.equals(NavType.GoIndex.OLDER.name())) {
            downloadInfo = DOWNLOAD_INFO_PARSER.getInfo(this.id.name(),
                    NavType.GoIndex.OLDER.name());
            downloadInfo.setExtraTasks(this::olderExtraTasks);
        } else {
            downloadInfo = DOWNLOAD_INFO_PARSER.getInfo(this.id.name(),
                    NavType.GoIndex.LIST_VIEW.name());
        }
        this.downloader = new Downloader(this.driver, this.args, navInfo, downloadInfo);
    }

    /**
     * Prepare a list of different navigational instructions for an OD
     *
     * @return list of different navigational variation for the OD; Otherwise an empty list;
     */
    @Override
    protected String[] prepareNavInfoTypes() {
        return new String[]{
                NavType.GoIndex.LIST_VIEW.name(),
                NavType.GoIndex.THUMBNAIL_VIEW.name(),
                NavType.GoIndex.OLDER.name()
        };
    }

    /**
     * Extra downloading instructions for Older versions
     *
     * @param driver {@link RemoteWebDriver}
     * @param dlinfo {@link DownloadInfo}
     */
    private void olderExtraTasks(RemoteWebDriver driver, DownloadInfo dlinfo) {
        Optional<WebElement> element = NavSupport.getElement(driver, dlinfo.getCssDownloadTask());
        if (element.isPresent()) {
            element.get().click();
            driver.navigate().back();
        }
    }
}