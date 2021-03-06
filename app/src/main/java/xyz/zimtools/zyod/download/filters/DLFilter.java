package xyz.zimtools.zyod.download.filters;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import xyz.zimtools.zyod.assets.info.DownloadInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

abstract class DLFilter implements DownloadFilter {
    private static final String FILE_ID_KEY = "file_ids";
    private static final String FILE_ATTRIBUTE_KEY = "attribute";
    protected static final String FILE_CHECK_KEY = "file_check";

    private List<WebElement> getWebFileElements(List<WebElement> elements, DownloadInfo downloadInfo) {
        List<WebElement> files = new ArrayList<>();
        String subElementCss = downloadInfo.getCssDownloadFilter().get(FILE_ID_KEY);
        if (subElementCss == null) {
            return elements;
        }
        for (WebElement el : elements) {
            WebElement odFileType =
                    el.findElement(By.cssSelector(subElementCss));
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
                    FILE_ATTRIBUTE_KEY));
            if (fileChecker.apply(fileType)) {
                filteredFiles.add(elements.get(i));
            }
        }
        return filteredFiles;
    }
}