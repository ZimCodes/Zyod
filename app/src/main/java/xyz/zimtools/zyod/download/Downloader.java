package xyz.zimtools.zyod.download;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import xyz.zimtools.zyod.AppConfig;
import xyz.zimtools.zyod.args.Args;
import xyz.zimtools.zyod.assets.info.DownloadInfo;
import xyz.zimtools.zyod.assets.info.NavInfo;
import xyz.zimtools.zyod.download.filters.DownloadFilter;
import xyz.zimtools.zyod.support.InteractSupport;
import xyz.zimtools.zyod.support.NavSupport;

import java.util.List;
import java.util.Optional;

/**
 * Downloads resources from an OD
 */
public final class Downloader {
    private final RemoteWebDriver driver;
    private final Args args;
    private final NavInfo navInfo;
    private final DownloadInfo downloadInfo;
    private final DownloadFilter filter;

    public Downloader(RemoteWebDriver driver, Args args, NavInfo navInfo,
                      DownloadInfo downloadInfo, DownloadFilter filter) {
        this.driver = driver;
        this.args = args;
        this.navInfo = navInfo;
        this.downloadInfo = downloadInfo;
        this.filter = filter;
    }

    public Downloader(RemoteWebDriver driver, Args args, NavInfo navInfo, DownloadInfo downloadInfo) {
        this(driver, args, navInfo, downloadInfo, null);
    }

    /**
     * Download files in the current URL.
     */
    private void download(List<WebElement> elements) {
        if (this.downloadInfo.getExtraTasks() != null) {
            this.multipleTasks(elements);
        } else {
            this.singleTask(elements);
        }
    }

    /**
     * Download the first located file.
     */
    public void singleDownload() {
        Optional<WebElement> element = this.getDownloadElement(this.driver);
        if (element.isEmpty()) {
            return;
        }
        List<WebElement> elList = List.of(element.get());
        this.download(elList);
    }

    /**
     * Download multiple files
     */
    public void multiDownload() {
        List<WebElement> elements = this.getDownloadElements(driver);
        if (elements.isEmpty()) {
            return;
        }

        if (this.filter != null) {
            elements = this.filter.apply(elements, this.downloadInfo);
        }
        this.download(elements);
    }

    /**
     * Downloads the first file using the context menu.
     */
    public void rightClickSingleDownload() {
        List<WebElement> elements = NavSupport.getElements(this.driver, this.navInfo.getCssFileSelector());
        if (elements.isEmpty()) {
            return;
        }
        if (this.filter != null) {
            elements = this.filter.apply(elements, this.downloadInfo);
        }

        if (elements.isEmpty()) {
            return;
        }

        WebElement validEl = elements.get(0);
        WebElement contextEl = this.getDownloadElements(driver).get(0);
        this.rightClick(contextEl, validEl);
    }

    /**
     * Downloads multiple files using the context menu.
     */
    public void rightClickMultiDownload() {
        List<WebElement> elements = NavSupport.getElements(this.driver, this.navInfo.getCssFileSelector());
        if (elements.isEmpty()) {
            return;
        }
        if (this.filter != null) {
            elements = this.filter.apply(elements, this.downloadInfo);
        }

        WebElement contextEl = this.getDownloadElements(driver).get(0);
        for (WebElement el : elements) {
            this.rightClick(contextEl, el);
        }
    }

    /**
     * Right-Click to download file
     */
    private void rightClick(WebElement contextEl, WebElement el) {
        if (this.args.getArgsInteractive().isScrolling()) {
            InteractSupport.scrollToElement(this.driver, el);
            long scrollWait = this.args.getArgsInteractive().getScrollWait();
            if (scrollWait > 0) {
                AppConfig.sleep(scrollWait);
            }
        }
        this.waiting();
        InteractSupport.rightClick(driver, el);
        contextEl.click();
    }


    public List<WebElement> getDownloadElements(RemoteWebDriver driver) {
        return NavSupport.getElements(driver, this.downloadInfo.getCssInitialDownload());
    }

    public Optional<WebElement> getDownloadElement(RemoteWebDriver driver) {
        return NavSupport.getElement(driver, this.downloadInfo.getCssInitialDownload());
    }

    /**
     * Download operations with a single action.
     *
     * @param elements list of {@link WebElement} to download.
     */
    private void singleTask(List<WebElement> elements) {
        for (int i = 0; i < elements.size(); i++) {
            this.waiting();
            List<WebElement> downloadEls = this.getDownloadElements(driver);
            if (this.filter != null) {
                downloadEls = this.filter.apply(downloadEls, this.downloadInfo);
            }
            downloadEls.get(i).click();
        }
    }

    /**
     * Downloading operations with multiple actions.
     *
     * @param elements list of {@link WebElement} to download.
     */
    private void multipleTasks(List<WebElement> elements) {
        for (int i = 0; i < elements.size(); i++) {
            this.waiting();
            if (i == 0) {
                elements.get(i).click();
            } else {
                List<WebElement> downloadEls = this.getDownloadElements(driver);
                if (this.filter != null) {
                    downloadEls = this.filter.apply(downloadEls, this.downloadInfo);
                }
                downloadEls.get(i).click();
            }
            this.downloadInfo.getExtraTasks().accept(this.driver);
        }
    }

    public void multipleTaskLazy(int index) {
        this.waiting();
        List<WebElement> downloadEls = this.getDownloadElements(this.driver);
        if (this.filter != null) {
            downloadEls = this.filter.apply(downloadEls, this.downloadInfo);
        }
        downloadEls.get(index).click();
        this.downloadInfo.getExtraTasks().accept(this.driver);
    }

    /**
     * Wait for a certain amount of time before performing a download action.
     * <p>
     * The action this method refers to is downloading. Wait a certain of time before
     * executing downloading task.
     * </p>
     */
    private void waiting() {
        long wait = this.args.getArgsDownload().getDownloadWait();
        if (wait > 0) {
            AppConfig.sleep(wait);
        }
    }


}