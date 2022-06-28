package xyz.zimtools.zyod.download.filters;

import org.openqa.selenium.WebElement;
import xyz.zimtools.zyod.assets.ODUrl;
import xyz.zimtools.zyod.assets.info.DownloadInfo;

import java.util.List;

public class FileDLFilter extends DLFilter {

    /**
     * Applies this function to the given arguments.
     *
     * @param elements     the first function argument
     * @param downloadInfo the second function argument
     * @return the function result
     */
    @Override
    public List<WebElement> apply(List<WebElement> elements, DownloadInfo downloadInfo) {
        return this.filterElements(elements, downloadInfo, ODUrl::isFile);
    }
}