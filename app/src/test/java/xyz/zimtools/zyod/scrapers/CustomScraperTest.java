package xyz.zimtools.zyod.scrapers;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.remote.RemoteWebDriver;
import xyz.zimtools.zyod.args.Args;
import xyz.zimtools.zyod.assets.Directory;
import xyz.zimtools.zyod.assets.ODUrl;
import xyz.zimtools.zyod.assets.info.NavInfoParser;
import xyz.zimtools.zyod.browsers.BrowserFactory;
import xyz.zimtools.zyod.fixtures.GlobalDefault;
import xyz.zimtools.zyod.fixtures.ODDemoRef;
import xyz.zimtools.zyod.fixtures.asserts.ScraperAssert;
import xyz.zimtools.zyod.od.ODType;
import xyz.zimtools.zyod.od.navigators.NavType;
import xyz.zimtools.zyod.scrapers.filters.OneDriveVercelIndexScrapeFilter;
import xyz.zimtools.zyod.scrapers.filters.ScrapeFilter;

import java.util.List;
import java.util.stream.Stream;

/**
 * Special scrapers made for a particular OD
 */
class CustomScraperTest {
    private static final String[] MAIN_ARGS = {"--headless"};
    private static Args args;
    private static NavInfoParser parser;
    private static RemoteWebDriver driver;

    @BeforeAll
    static void beforeAll() {
        parser = new NavInfoParser();
    }

    @AfterAll
    static void afterAll() {
        driver.quit();
    }

    private void init(String url) {
        args = new Args(GlobalDefault.joinArr(new String[][]{new String[]{url}, MAIN_ARGS}));
        driver = BrowserFactory.getBrowser(args).getDriver();
        driver.get(url);
    }

    @ParameterizedTest
    @MethodSource("getParams")
    void scrapeTest(String url, ODType odType, String navType, int dirCount, int fileCount,
                    ScrapeFilter filter) {
        this.init(url);
        Scraper scraper = new OneDriveVercelIndexScraper(driver, args, parser.getInfo(odType.name(), navType),
                filter);
        scraper.scrape(List.of(), new Directory(1, new ODUrl(url)));
        ScraperAssert.resourceCount(dirCount, scraper.getDirs().size(), "directories", url);
        ScraperAssert.resourceCount(fileCount, scraper.getFiles().size(), "files", url);
    }

    private static Stream<Arguments> getParams() {
        return Stream.of(
                Arguments.of(ODDemoRef.ONEDRIVE_VERCEL_INDEX, ODType.ONEDRIVE_VERCEL_INDEX,
                        NavType.Onedrive_Vercel_Index.LIST_VIEW.name(), 10, 1,
                        new OneDriveVercelIndexScrapeFilter())
        );
    }
}