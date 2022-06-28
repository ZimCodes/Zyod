package xyz.zimtools.zyod.od.navigators;

import org.openqa.selenium.remote.RemoteWebDriver;
import xyz.zimtools.zyod.args.Args;
import xyz.zimtools.zyod.assets.info.NavInfo;
import xyz.zimtools.zyod.od.ODType;
import xyz.zimtools.zyod.scrapers.AttributeScraper;

public class GDIndexNav extends ODNavigator {

    public GDIndexNav(RemoteWebDriver driver, Args args) {
        super(ODType.GDINDEX, driver, args);
    }

    /**
     * Setup Scraper dependencies
     */
    @Override
    protected void setUpScraper(NavInfo navInfo, String navType) {
        this.scraper = new AttributeScraper(this.driver, this.args, navInfo);
    }

    /**
     * Prepare a list of different navigational instructions for an OD
     *
     * @return list of different navigational variation for the OD; Otherwise an empty list;
     */
    @Override
    protected String[] prepareNavInfoTypes() {
        return new String[]{NavType.GDIndex.MAIN.name()};
    }
}