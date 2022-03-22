package xyz.zimtools.zyod.download.filters;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import xyz.zimtools.zyod.assets.info.DownloadInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

abstract class DLFilter implements DownloadFilter {
    protected static final String FILE_CHECK_KEY = "file_check";

    private List<WebElement> getWebFileElements(List<WebElement> elements, DownloadInfo downloadInfo) {
        List<WebElement> files = new ArrayList<>();
        for (WebElement el : elements) {
            WebElement odFileType =
                    el.findElement(By.cssSelector(downloadInfo.getCssDownloadFilter().get(
                            "file_ids")));
            files.add(odFileType);
        }
        return files;
    }

    /**
     * Filter out files that do not need to be downloaded.
     *
     * @param elements {@link WebElement} found on current page
     */
    protected List<WebElement> filterElements(List<WebElement> elements,
                                              DownloadInfo downloadInfo,
                                              Function<String, Boolean> fileChecker) {
        List<WebElement> files = this.getWebFileElements(elements, downloadInfo);
        List<WebElement> filteredFiles = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            String fileType = files.get(i).getAttribute(downloadInfo.getCssDownloadFilter().get(
                    "attribute"));
            if (fileChecker.apply(fileType)) {
                filteredFiles.add(elements.get(i));
            }
        }
        return filteredFiles;
    }
}