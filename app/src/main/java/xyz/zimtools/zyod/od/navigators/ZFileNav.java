package xyz.zimtools.zyod.od.navigators;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import xyz.zimtools.zyod.args.Args;
import xyz.zimtools.zyod.assets.Directory;
import xyz.zimtools.zyod.assets.ODUrl;
import xyz.zimtools.zyod.assets.info.NavInfo;
import xyz.zimtools.zyod.od.ODType;
import xyz.zimtools.zyod.scrapers.SiftScraper;
import xyz.zimtools.zyod.scrapers.filters.GenericScrapeFilter;
import xyz.zimtools.zyod.support.NavSupport;

import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZFileNav extends NameNavigator {
    private static final Pattern URL_PATH_PAT;

    static {
        URL_PATH_PAT = Pattern.compile("/[1-9]/main");
    }

    public ZFileNav(RemoteWebDriver driver, Args args) {
        super(ODType.ZFILE, driver, args);
    }

    /**
     * Setup Scraper dependencies
     */
    @Override
    protected void setUpScraper(NavInfo navInfo, String navType) {
        this.scraper = new SiftScraper(this.driver, this.args, navInfo, new GenericScrapeFilter());
    }

    /**
     * Prepare a list of different navigational instructions for an OD
     *
     * @return list of different navigational variation for the OD; Otherwise an empty list;
     */
    @Override
    protected String[] prepareNavInfoTypes() {
        return new String[]{NavType.ZFile.MAIN.name()};
    }

    /**
     * Begin navigating an OD.
     *
     * @param directory {@link Directory} to navigate to
     */
    @Override
    public void navigate(Directory directory) {
        Matcher matcher = URL_PATH_PAT.matcher(directory.toString());
        if (!matcher.find()) {
            directory = new Directory(0, new ODUrl(this.driver.getCurrentUrl()));
        }
        super.navigate(directory);
    }

    @Override
    protected Map<String, Object> goToDirectory(Directory directory) {
        super.goToDirectory(directory);
        this.waiting();
        String cssDialogBox = "div.el-message-box__wrapper";
        Optional<WebElement> downloadDialogBox = NavSupport.getElement(this.driver, cssDialogBox);
        if (downloadDialogBox.isPresent()) {
            String displayStatus = downloadDialogBox.get().getAttribute("style");
            return Map.of(NOT_LOGIN_KEY, displayStatus.contains("none"), DIRECTORY_KEY, directory);
        }
        return Map.of(NOT_LOGIN_KEY, true, DIRECTORY_KEY, directory);
    }
}