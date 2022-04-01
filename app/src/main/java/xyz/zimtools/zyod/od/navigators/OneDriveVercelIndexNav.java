package xyz.zimtools.zyod.od.navigators;

import org.openqa.selenium.remote.RemoteWebDriver;
import xyz.zimtools.zyod.args.Args;
import xyz.zimtools.zyod.assets.info.NavInfo;
import xyz.zimtools.zyod.od.ODType;
import xyz.zimtools.zyod.scrapers.OneDriveVercelIndexScraper;
import xyz.zimtools.zyod.scrapers.TextScraper;
import xyz.zimtools.zyod.scrapers.filters.OneDriveVercelIndexScrapeFilter;

public class OneDriveVercelIndexNav extends ODNavigator {

    public OneDriveVercelIndexNav(RemoteWebDriver driver, Args args) {
        super(ODType.ONEDRIVE_VERCEL_INDEX, driver, args);
    }

    /**
     * Setup Scraper dependencies
     */
    @Override
    protected void setUpScraper(NavInfo navInfo, String navType) {
        if (navType.equals(NavType.Onedrive_Vercel_Index.LIST_VIEW.name())
                || navType.equals(NavType.Onedrive_Vercel_Index.THUMBNAIL_VIEW.name())) {
            this.scraper = new OneDriveVercelIndexScraper(this.driver, this.args, navInfo,
                    new OneDriveVercelIndexScrapeFilter());
        } else {
            this.scraper = new TextScraper(this.driver, this.args, navInfo,
                    new OneDriveVercelIndexScrapeFilter());
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
                NavType.Onedrive_Vercel_Index.LIST_VIEW.name(),
                NavType.Onedrive_Vercel_Index.THUMBNAIL_VIEW.name(),
                NavType.Onedrive_Vercel_Index.OLDER.name(),
                NavType.Onedrive_Vercel_Index.NO_THUMBNAIL_OPTION.name()
        };
    }


}