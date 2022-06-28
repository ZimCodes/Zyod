package xyz.zimtools.zyod.od.navigators;

import org.openqa.selenium.remote.RemoteWebDriver;
import xyz.zimtools.zyod.args.Args;
import xyz.zimtools.zyod.assets.info.NavInfo;
import xyz.zimtools.zyod.od.ODType;
import xyz.zimtools.zyod.scrapers.TextScraper;

public class YukiDriveNav extends ODNavigator {

    public YukiDriveNav(RemoteWebDriver driver, Args args) {
        super(ODType.YUKIDRIVE, driver, args);
    }

    /**
     * Setup Scraper dependencies
     */
    @Override
    protected void setUpScraper(NavInfo navInfo, String navType) {
        this.scraper = new TextScraper(this.driver, this.args, navInfo);
    }

    /**
     * Prepare a list of different navigational instructions for an OD
     *
     * @return list of different navigational variation for the OD; Otherwise an empty list;
     */
    @Override
    protected String[] prepareNavInfoTypes() {
        return new String[]{NavType.YukiDrive.MAIN.name()};
    }
}