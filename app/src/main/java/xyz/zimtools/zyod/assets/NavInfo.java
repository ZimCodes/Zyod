package xyz.zimtools.zyod.assets;

import org.openqa.selenium.remote.RemoteWebDriver;
import xyz.zimtools.zyod.od.navigators.NavType;

import java.util.function.Consumer;

/**
 * Holds navigation/navigator details.
 */
public final class NavInfo {
    private final NavType id;
    private final String cssFileSelector;
    private final String cssFileName;
    private final String cssAttr;
    private final String cssInitialDownload;
    private final String cssRejectFilter;
    private final String waitErrorMessage;
    private final Consumer<RemoteWebDriver> extraTasks;

    private NavInfo(NavType id, String cssFileSelector, String cssFileName, String cssAttr,
                    String cssInitialDownload, String cssRejectFilter, String waitErrorMessage,
                    Consumer<RemoteWebDriver> extraTasks) {
        this.id = id;
        this.cssFileSelector = cssFileSelector;
        this.cssFileName = cssFileName;
        this.cssAttr = cssAttr;
        this.cssInitialDownload = cssInitialDownload;
        this.cssRejectFilter = cssRejectFilter;
        this.waitErrorMessage = waitErrorMessage;
        this.extraTasks = extraTasks;
    }

    public NavType getId() {
        return id;
    }

    public String getCssFileSelector() {
        return cssFileSelector;
    }

    public String getCssFileName() {
        return cssFileName;
    }

    public String getCssAttr() {
        return cssAttr;
    }

    public String getCssInitialDownload() {
        return cssInitialDownload;
    }

    public String getCssRejectFilter() {
        return cssRejectFilter;
    }

    public String getWaitErrorMessage() {
        return waitErrorMessage;
    }

    public Consumer<RemoteWebDriver> getExtraTasks() {
        return extraTasks;
    }

    public static class Builder {
        private NavType id;
        private String cssFileSelector;
        private String cssFileName;
        private String cssAttr;
        private String cssInitialDownload;
        private String cssRejectFilter;
        private String waitErrorMessage;
        private Consumer<RemoteWebDriver> extraTasks;

        public Builder() {
        }

        /**
         * Sets name of navigational method type.
         * */
        public Builder setId(NavType id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the CSS path used to navigate between files.
         * */
        public Builder setCssFileSelector(String cssFileSelector) {
            this.cssFileSelector = cssFileSelector;
            return this;
        }

        /**
         * Sets the CSS path used to identify the name of the files.
         * */
        public Builder setCssFileName(String cssFileName) {
            this.cssFileName = cssFileName;
            return this;
        }

        /**
         * Sets the CSS attribute that contains a reference (link) to a file
         * */
        public Builder setCssAttr(String cssAttr) {
            this.cssAttr = cssAttr;
            return this;
        }

        /**
         * Sets the first CSS path element to interact with in order to start download process.
         * */
        public Builder setCssInitialDownload(String cssInitialDownload) {
            this.cssInitialDownload = cssInitialDownload;
            return this;
        }

        /**
         * Sets the CSS path elements found in
         * {@link xyz.zimtools.zyod.assets.NavInfo#cssFileSelector}.
         * */
        public Builder setCssRejectFilter(String cssRejectFilter) {
            this.cssRejectFilter = cssRejectFilter;
            return this;
        }

        /**
         * Sets error message to show if no elements are found or present.
         * */
        public Builder setWaitErrorMessage(String waitErrorMessage) {
            this.waitErrorMessage = waitErrorMessage;
            return this;
        }

        /**
         * Sets extra tasks to perform after clicking the first element (from:
         * {@link xyz.zimtools.zyod.assets.NavInfo#cssInitialDownload}) to download.
         * */
        public Builder setExtraTasks(Consumer<RemoteWebDriver> extraTasks) {
            this.extraTasks = extraTasks;
            return this;
        }

        public NavInfo build() {
            return new NavInfo(this.id, this.cssFileSelector, this.cssFileName, this.cssAttr,
                    this.cssInitialDownload, this.cssRejectFilter, this.waitErrorMessage,
                    this.extraTasks);
        }
    }
}