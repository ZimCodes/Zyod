package xyz.zimtools.zyod.od.navigators;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import xyz.zimtools.zyod.args.Args;
import xyz.zimtools.zyod.assets.info.DownloadInfo;
import xyz.zimtools.zyod.assets.info.NavInfo;
import xyz.zimtools.zyod.download.Downloader;
import xyz.zimtools.zyod.download.filters.ShareListDLFilter;
import xyz.zimtools.zyod.download.filters.ShareListListDLFilter;
import xyz.zimtools.zyod.download.filters.ShareListPreviewDLFilter;
import xyz.zimtools.zyod.od.ODType;
import xyz.zimtools.zyod.scrapers.AttributeScraper;
import xyz.zimtools.zyod.scrapers.filters.GenericScrapeFilter;
import xyz.zimtools.zyod.support.NavSupport;

import java.util.Optional;

public class ShareListNav extends ODNavigator {

    public ShareListNav(RemoteWebDriver driver, Args args) {
        super(ODType.SHARELIST, driver, args);
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
        DownloadInfo downloadInfo;
        if (navType.equals(NavType.ShareList.DOWNLOAD_QUERY.name())) {
            downloadInfo = DOWNLOAD_INFO_PARSER.getInfo(this.id.name(),
                    NavType.ShareList.DOWNLOAD_QUERY.name());
            this.downloader = new Downloader(this.driver, this.args, navInfo, downloadInfo,
                    new ShareListDLFilter());
        } else if (navType.equals(NavType.ShareList.PREVIEW_QUERY.name())) {
            downloadInfo = DOWNLOAD_INFO_PARSER.getInfo(this.id.name(),
                    NavType.ShareList.PREVIEW_QUERY.name());
            downloadInfo.setExtraTasks(this::previewTask);
            this.downloader = new Downloader(this.driver, this.args, navInfo, downloadInfo,
                    new ShareListPreviewDLFilter());
        } else if (navType.equals(NavType.ShareList.NO_HREF.name())) {
            downloadInfo = DOWNLOAD_INFO_PARSER.getInfo(this.id.name(),
                    NavType.ShareList.NO_HREF.name());
            this.downloader = new Downloader(this.driver, this.args, navInfo, downloadInfo, new ShareListDLFilter());
        } else if (navType.equals(NavType.ShareList.LIST.name())) {
            downloadInfo = DOWNLOAD_INFO_PARSER.getInfo(this.id.name(),
                    NavType.ShareList.LIST.name());
            this.downloader = new Downloader(this.driver, this.args, navInfo, downloadInfo,
                    new ShareListListDLFilter());
        } else {
            downloadInfo = DOWNLOAD_INFO_PARSER.getInfo(this.id.name(),
                    NavType.ShareList.INTERACTIVE.name());
            downloadInfo.setExtraTasks(this::interactiveTask);
            this.downloader = new Downloader(this.driver, this.args, navInfo, downloadInfo, new ShareListDLFilter());
        }
    }

    /**
     * Prepare a list of different navigational instructions for an OD
     *
     * @return list of different navigational variation for the OD; Otherwise an empty list;
     */
    @Override
    protected String[] prepareNavInfoTypes() {
        return new String[]{
                NavType.ShareList.DOWNLOAD_QUERY.name(),
                NavType.ShareList.NO_HREF.name(),
                NavType.ShareList.INTERACTIVE.name(),
                NavType.ShareList.PREVIEW_QUERY.name(),
                NavType.ShareList.LIST.name()
        };
    }

    /**
     * Extra downloading instructions for ShareList preview variant
     *
     * @param driver {@link RemoteWebDriver}
     * @param dlInfo {@link DownloadInfo}
     */
    private void previewTask(RemoteWebDriver driver, DownloadInfo dlInfo) {
        this.waiting();
        int totalWindows = driver.getWindowHandles().size();
        if (totalWindows > 1) {
            String nextTab = driver.getWindowHandles().stream().toList().get(1);
            driver.switchTo().window(nextTab);
        }
        Optional<WebElement> element = NavSupport.getElement(driver, dlInfo.getCssDownloadTask());
        if (element.isPresent()) {
            this.waiting();
            element.get().click();
            driver.close();
            driver.switchTo().window(driver.getWindowHandles().stream().toList().get(0));
        }
    }

    /**
     * Extra downloading instructions for ShareList interactive variant
     *
     * @param driver {@link RemoteWebDriver}
     * @param dlInfo {@link DownloadInfo}
     */
    private void interactiveTask(RemoteWebDriver driver, DownloadInfo dlInfo) {
        this.waiting();
        while (driver.getWindowHandles().size() > 1) {
            String lastTab =
                    driver.getWindowHandles().stream().toList().get(driver.getWindowHandles().size() - 1);
            driver.switchTo().window(lastTab);
            driver.close();
        }
        driver.switchTo().window(driver.getWindowHandles().stream().toList().get(0));
    }
}