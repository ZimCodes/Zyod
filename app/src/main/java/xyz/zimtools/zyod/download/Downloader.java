package xyz.zimtools.zyod.download;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import xyz.zimtools.zyod.AppConfig;
import xyz.zimtools.zyod.args.Args;
import xyz.zimtools.zyod.assets.NavInfo;
import xyz.zimtools.zyod.support.InteractSupport;
import xyz.zimtools.zyod.support.NavSupport;

import java.util.List;

/**
 * Downloads resources from an OD
 */
public final class Downloader {
    private final RemoteWebDriver driver;
    private final Args args;
    private final NavInfo navInfo;
    private final DownloadFilter filter;

    public Downloader(RemoteWebDriver driver, Args args, NavInfo navInfo, DownloadFilter filter) {
        this.driver = driver;
        this.args = args;
        this.navInfo = navInfo;
        this.filter = filter;
    }

    public Downloader(RemoteWebDriver driver, Args args, NavInfo navInfo) {
        this(driver, args, navInfo, null);
    }

    /**
     * Download files in the current URL
     */
    public void download() {
        List<WebElement> elements = this.getDownloadElements(driver);
        if (elements.isEmpty()) {
            return;
        }
        if (this.filter != null) {
            elements = this.filter.apply(elements);
        }

        if (this.navInfo.getExtraTasks() != null) {
            this.multipleTasks(elements);
        } else {
            this.singleTask(elements);
        }
    }

    /**
     * Downloads files using the context menu.
     */
    public void rightClickDownload() {
        List<WebElement> elements = NavSupport.getElements(this.driver, this.navInfo.getCssFileSelector());
        if (this.filter != null) {
            elements = this.filter.apply(elements);
        }
        WebElement contextEl = this.getDownloadElements(driver).get(0);
        for (WebElement el : elements) {
            if (this.args.getArgsInteractive().isScrolling()) {
                InteractSupport.scrollToElement(this.driver, el);
                long scrollWait = this.args.getArgsInteractive().getScrollWait();
                if (scrollWait > 0) {
                    AppConfig.sleep(scrollWait);
                }
            }
            InteractSupport.rightClick(driver, el);
            contextEl.click();
        }
    }

    public List<WebElement> getDownloadElements(RemoteWebDriver driver) {
        return NavSupport.getElements(driver, this.navInfo.getCssInitialDownload());
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
                downloadEls = this.filter.apply(downloadEls);
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
                    downloadEls = this.filter.apply(downloadEls);
                }
                downloadEls.get(i).click();
            }
            this.navInfo.getExtraTasks().accept(this.driver);
        }
    }

    public void multipleTaskLazy(int index) {
        this.waiting();
        List<WebElement> downloadEls = this.getDownloadElements(this.driver);
        if (this.filter != null) {
            downloadEls = this.filter.apply(downloadEls);
        }
        downloadEls.get(index).click();
        this.navInfo.getExtraTasks().accept(this.driver);
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