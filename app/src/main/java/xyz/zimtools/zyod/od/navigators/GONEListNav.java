package xyz.zimtools.zyod.od.navigators;

import org.openqa.selenium.remote.RemoteWebDriver;
import xyz.zimtools.zyod.args.Args;
import xyz.zimtools.zyod.assets.info.DownloadInfo;
import xyz.zimtools.zyod.assets.info.NavInfo;
import xyz.zimtools.zyod.download.Downloader;
import xyz.zimtools.zyod.download.filters.GONEListDLFilter;
import xyz.zimtools.zyod.od.ODType;
import xyz.zimtools.zyod.scrapers.AttributeScraper;

public class GONEListNav extends ODNavigator {

    public GONEListNav(RemoteWebDriver driver, Args args) {
        super(ODType.GONELIST, driver, args);
    }

    /**
     * Setup Scraper dependencies
     */
    @Override
    protected void setUpScraper(NavInfo navInfo, String navType) {
        this.scraper = new AttributeScraper(this.driver, this.args, navInfo);
    }

    /**
     * Setup {@link Downloader} dependencies.
     */
    @Override
    protected void setUpDownloader(NavInfo navInfo, String navType) {
        DownloadInfo downloadInfo = DOWNLOAD_INFO_PARSER.getInfo(this.id.name(), NavType.GONEList.MAIN.name());
        this.downloader = new Downloader(this.driver, this.args, navInfo, downloadInfo,
                new GONEListDLFilter());
    }

    /**
     * Prepare a list of different navigational instructions for an OD
     *
     * @return list of different navigational variation for the OD; Otherwise an empty list;
     */
    @Override
    protected String[] prepareNavInfoTypes() {
        return new String[]{NavType.GONEList.MAIN.name()};
    }
}