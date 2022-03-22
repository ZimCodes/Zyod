package xyz.zimtools.zyod.download.filters;

import org.openqa.selenium.WebElement;
import xyz.zimtools.zyod.assets.info.DownloadInfo;

import java.util.List;

/**
 * Download filter for ShareList Download type
 */
public final class ShareListDLFilter extends DLFilter {

    @Override
    public List<WebElement> apply(List<WebElement> elements, DownloadInfo downloadInfo) {
        return this.filterElements(elements, downloadInfo,
                fileType -> !downloadInfo.getCssDownloadFilter().get(FILE_CHECK_KEY).equals(fileType));
    }
}