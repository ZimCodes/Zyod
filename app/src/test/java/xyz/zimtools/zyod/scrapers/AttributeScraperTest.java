package xyz.zimtools.zyod.scrapers;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.remote.RemoteWebDriver;
import xyz.zimtools.zyod.AppConfig;
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
import xyz.zimtools.zyod.scrapers.filters.GenericScrapeFilter;
import xyz.zimtools.zyod.scrapers.filters.ScrapeFilter;

import java.util.List;
import java.util.stream.Stream;

/**
 * Tests {@link AttributeScraper}
 */
class AttributeScraperTest {
    private static final String[] MAIN_ARGS = {"--headless","--all-certs","--page-wait","60000"};
    private static NavInfoParser parser;
    private static RemoteWebDriver driver;
    private Scraper scraper;

    @BeforeAll
    static void beforeAll() {
        parser = new NavInfoParser();
    }

    @AfterAll
    static void cleanUp() {
        driver.quit();
    }

    private void init(String url, String odType, String navType, ScrapeFilter filter) {
        Args args = new Args(GlobalDefault.joinArr(new String[][]{new String[]{url}, MAIN_ARGS}));
        driver = BrowserFactory.getBrowser(args).getDriver();
        driver.get(url);
        AppConfig.sleep(7000);
        scraper = new AttributeScraper(driver, args, parser.getInfo(odType, navType), filter);
        scraper.scrape(List.of(), new Directory(1, new ODUrl(url)));
    }

    @ParameterizedTest
    @MethodSource("getParams")
    void scrapeTest(String url, ODType odType, String navType, int dirCount, int fileCount,
                    ScrapeFilter filter) {
        this.init(url, odType.name(), navType, filter);
        ScraperAssert.resourceCount(dirCount, scraper.getDirs().size(), "directories", url);
        ScraperAssert.resourceCount(fileCount, scraper.getFiles().size(), "files", url);
    }

    private static Stream<Arguments> getParams() {
        return Stream.of(
                Arguments.of(ODDemoRef.GO_INDEX, ODType.GOINDEX, NavType.GoIndex.LIST_VIEW.name()
                        , 9, 1, new GenericScrapeFilter()),
                Arguments.of(ODDemoRef.GD_INDEX, ODType.GDINDEX, NavType.GDIndex.MAIN.name(), 5,
                        4, null),
                Arguments.of(ODDemoRef.ALIST, ODType.ALIST, NavType.AList.ORIGINAL.name()
                        , 11, 0, null)
        );
    }
}