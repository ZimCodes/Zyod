package xyz.zimtools.zyod.download.filters;

import org.openqa.selenium.WebElement;
import xyz.zimtools.zyod.assets.info.DownloadInfo;

import java.util.List;

public final class GONEListDLFilter extends DLFilter {

    @Override
    public List<WebElement> apply(List<WebElement> elements, DownloadInfo downloadInfo) {
        return this.filterElements(elements, downloadInfo,
                fileType -> !downloadInfo.getCssDownloadFilter().get(FILE_CHECK_KEY).contains(fileType)
        );
    }
}