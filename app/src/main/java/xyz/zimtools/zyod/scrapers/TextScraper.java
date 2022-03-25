package xyz.zimtools.zyod.scrapers;

import org.openqa.selenium.remote.RemoteWebDriver;
import xyz.zimtools.zyod.args.Args;
import xyz.zimtools.zyod.assets.info.NavInfo;
import xyz.zimtools.zyod.scrapers.filters.ScrapeFilter;

public class TextScraper extends JoinScraper {

    public TextScraper(RemoteWebDriver driver, Args args, NavInfo navInfo, ScrapeFilter filter) {
        super(driver, args, navInfo, filter);
    }

    public TextScraper(RemoteWebDriver driver, Args args, NavInfo navInfo) {
        super(driver, args, navInfo);
    }
}