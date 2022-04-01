package xyz.zimtools.zyod.od.navigators;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import xyz.zimtools.zyod.args.Args;
import xyz.zimtools.zyod.assets.info.DownloadInfo;
import xyz.zimtools.zyod.assets.info.NavInfo;
import xyz.zimtools.zyod.download.Downloader;
import xyz.zimtools.zyod.download.filters.FODIDLFilter;
import xyz.zimtools.zyod.od.ODType;
import xyz.zimtools.zyod.scrapers.TextScraper;
import xyz.zimtools.zyod.support.NavSupport;

import java.util.List;
import java.util.Optional;

public class FODINav extends TouchNavigator {

    public FODINav(RemoteWebDriver driver, Args args) {
        super(ODType.FODI, driver, args);
    }

    /**
     * Setup Scraper dependencies
     */
    @Override
    protected void setUpScraper(NavInfo navInfo, String navType) {
        this.scraper = new TextScraper(this.driver, this.args, navInfo);
    }

    /**
     * Setup {@link Downloader} dependencies.
     */
    @Override
    protected void setUpDownloader(NavInfo navInfo, String navType) {
        DownloadInfo downloadInfo = DOWNLOAD_INFO_PARSER.getInfo(ODType.FODI.name(), navType);
        downloadInfo.setExtraTasks(this::mainExtraTask);
        this.downloader = new Downloader(this.driver, this.args, navInfo, downloadInfo, new FODIDLFilter());
    }

    /**
     * Download contents from current directory
     */
    @Override
    protected void download() {
        List<WebElement> elements = this.downloader.getDownloadElements(this.driver);
        elements = this.downloader.applyFilter(elements);
        for (int i = 0; i < elements.size(); i++) {
            this.downloader.downloadTaskLazy(i);
            this.curDir.resetDepthLevel();
            this.moveDownToDestination(this.curDir);
        }
    }

    /**
     * Prepare a list of different navigational instructions for an OD
     *
     * @return list of different navigational variation for the OD; Otherwise an empty list;
     */
    @Override
    protected String[] prepareNavInfoTypes() {
        return new String[]{NavType.FODI.MAIN.name()};
    }


    /**
     * Extra downloading instructions for MAIN variant of FODI
     *
     * @param driver {@link RemoteWebDriver}
     */
    private void mainExtraTask(RemoteWebDriver driver, DownloadInfo dlInfo) {
        this.waiting();
        Optional<WebElement> element = NavSupport.getElement(driver, dlInfo.getCssDownloadTask());
        if (element.isPresent()) {
            element.get().click();
            this.goBackButton();
        }
    }

    /**
     * Interaction with the OD to go back a page
     *
     * @return True if back at home page; false otherwise
     */
    @Override
    protected boolean goBackButton() {
        String cssBack;
        if (this.scraper != null) {
            cssBack = this.scraper.getNavInfo().getCssBackMap().get(NavInfo.BACK_BTN_KEY);
        } else {
            cssBack = NAV_INFO_PARSER
                    .getInfo(ODType.FODI.name(), NavType.FODI.MAIN.name())
                    .getCssBackMap()
                    .get(NavInfo.BACK_BTN_KEY);
        }
        Optional<WebElement> backBtn = NavSupport.getElement(this.driver, cssBack);
        String arrowStatus = backBtn.get().getAttribute("style");
        if (arrowStatus.contains("black")) {
            backBtn.get().click();
            return false;
        } else {
            Optional<WebElement> homeBtn = NavSupport.getElement(this.driver,
                    this.scraper.getNavInfo().getCssBackMap().get(NavInfo.HOME_BTN_KEY));
            homeBtn.get().click();
            return true;
        }
    }
}