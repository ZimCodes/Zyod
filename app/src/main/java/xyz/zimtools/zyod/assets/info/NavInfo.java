package xyz.zimtools.zyod.assets.info;

import java.util.Map;
import java.util.Objects;

/**
 * Holds navigation/navigator details.
 */
public final class NavInfo {
    private final String id;
    private final String navType;
    private final String cssFileSelector;
    private final String cssFileName;
    private final String cssAttr;
    private final String cssRejectFilter;
    private final Map<String, String> cssBackMap;
    private final String waitErrorMessage;
    public static final String BACK_BTN_KEY = "back_btn";
    public static final String HOME_BTN_KEY = "home_btn";

    private NavInfo(String id, String navType, String cssFileSelector, String cssFileName,
                    String cssAttr, String cssRejectFilter,
                    Map<String, String> cssBackMap, String waitErrorMessage) {
        this.id = id;
        this.navType = navType;
        this.cssFileSelector = cssFileSelector;
        this.cssFileName = cssFileName;
        this.cssAttr = cssAttr;
        this.cssRejectFilter = cssRejectFilter;
        this.cssBackMap = cssBackMap;
        this.waitErrorMessage = waitErrorMessage;
    }

    public String getId() {
        return id;
    }

    public String getNavType() {
        return this.navType;
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

    public String getCssRejectFilter() {
        return cssRejectFilter;
    }

    public Map<String, String> getCssBackMap() {
        return cssBackMap;
    }

    public String getWaitErrorMessage() {
        return waitErrorMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NavInfo navInfo = (NavInfo) o;
        return id.equals(navInfo.id) && navType.equals(navInfo.navType) && cssFileSelector.equals(navInfo.cssFileSelector) && Objects.equals(cssFileName, navInfo.cssFileName) && Objects.equals(cssAttr, navInfo.cssAttr) && Objects.equals(cssRejectFilter, navInfo.cssRejectFilter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, navType, cssFileSelector, cssFileName, cssAttr, cssRejectFilter);
    }


    public static class Builder {
        private String id;
        private String navType;
        private String cssFileSelector;
        private String cssFileName;
        private String cssAttr;
        private String cssRejectFilter;
        private Map<String, String> cssBackMap;
        private String waitErrorMessage;

        public Builder() {
            this.cssAttr = "href";
            this.waitErrorMessage = "Navigation method failed!";
        }

        /**
         * Sets name of navigational method type.
         */
        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the navigation type
         */
        public Builder setNavType(String navType) {
            this.navType = navType;
            return this;
        }

        /**
         * Sets the CSS path used to navigate between files.
         */
        public Builder setCssFileSelector(String cssFileSelector) {
            this.cssFileSelector = cssFileSelector;
            return this;
        }

        /**
         * Sets the CSS path used to identify the name of the files.
         */
        public Builder setCssFileName(String cssFileName) {
            this.cssFileName = cssFileName;
            return this;
        }

        /**
         * Sets the CSS attribute that contains a reference (link) to a file
         */
        public Builder setCssAttr(String cssAttr) {
            this.cssAttr = cssAttr;
            return this;
        }

        /**
         * Sets the CSS path elements found in
         * {@link xyz.zimtools.zyod.assets.info.NavInfo#cssFileSelector} to be rejected.
         * <p>
         * All elements in this CSS path will be filtered out from the scraper.;
         * </p>
         */
        public Builder setCssRejectFilter(String cssRejectFilter) {
            this.cssRejectFilter = cssRejectFilter;
            return this;
        }

        /**
         * CSS path to elements allowing to go back home or a previous page.
         * <p>
         * This option is used mostly for navigators that a derive from TouchNavigation
         * </p>
         */
        public Builder setCssBackMap(Map<String, String> cssBackMap) {
            this.cssBackMap = cssBackMap;
            return this;
        }

        /**
         * Sets error message to show if no elements are found or present.
         */
        public Builder setWaitErrorMessage(String waitErrorMessage) {
            this.waitErrorMessage = waitErrorMessage;
            return this;
        }

        public NavInfo build() {
            this.waitErrorMessage = String.format("%s navigation method failed!", this.navType);
            return new NavInfo(this.id, this.navType, this.cssFileSelector, this.cssFileName,
                    this.cssAttr,
                    this.cssRejectFilter,
                    this.cssBackMap,
                    this.waitErrorMessage);
        }
    }
}