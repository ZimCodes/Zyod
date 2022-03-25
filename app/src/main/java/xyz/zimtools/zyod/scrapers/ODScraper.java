package xyz.zimtools.zyod.scrapers;

import org.openqa.selenium.WebElement;
import xyz.zimtools.zyod.assets.Directory;
import xyz.zimtools.zyod.assets.ODUrl;
import xyz.zimtools.zyod.assets.info.NavInfo;

import java.util.List;

public interface ODScraper {
    List<WebElement> scrape(List<WebElement> elements, Directory currentDir);

    List<WebElement> scrapeItems();

    NavInfo getNavInfo();

    List<ODUrl> getFiles();

    List<Directory> getDirs();

    String applyFilter(String text);

    void reset();
}