package xyz.zimtools.zyod.download.filters;

import org.openqa.selenium.WebElement;
import xyz.zimtools.zyod.assets.info.DownloadInfo;

import java.util.List;

public final class GONEListDLFilter extends DLFilter {

    @Override
    public List<WebElement> apply(List<WebElement> elements, DownloadInfo downloadInfo) {
        return this.filterElements(elements, downloadInfo,
                fileType -> !fileType.contains(downloadInfo.getCssDownloadFilter().get(FILE_CHECK_KEY))
        );
    }
}