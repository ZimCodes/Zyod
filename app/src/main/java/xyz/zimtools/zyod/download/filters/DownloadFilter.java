package xyz.zimtools.zyod.download.filters;

import org.openqa.selenium.WebElement;
import xyz.zimtools.zyod.assets.info.DownloadInfo;

import java.util.List;
import java.util.function.BiFunction;

/**
 * Filter out content found in an OD to be downloaded.
 */
@FunctionalInterface
public interface DownloadFilter extends BiFunction<List<WebElement>, DownloadInfo, List<WebElement>> {
}