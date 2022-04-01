package xyz.zimtools.zyod.download.filters;

import org.openqa.selenium.WebElement;
import xyz.zimtools.zyod.assets.info.DownloadInfo;

import java.util.List;

/**
 * Download filter for the List variant of ShareList
 */
public class ShareListListDLFilter extends DLFilter {

    /**
     * Applies this function to the given arguments.
     *
     * @param elements     the first function argument
     * @param downloadInfo the second function argument
     * @return the function result
     */
    @Override
    public List<WebElement> apply(List<WebElement> elements, DownloadInfo downloadInfo) {
        return this.filterElements(elements, downloadInfo,
                fileType -> downloadInfo.getCssDownloadFilter().get(FILE_CHECK_KEY).equals(fileType));
    }
}