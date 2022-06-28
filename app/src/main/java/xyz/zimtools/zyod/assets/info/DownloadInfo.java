package xyz.zimtools.zyod.assets.info;

import java.util.Map;
import java.util.Objects;

public final class DownloadInfo {
    private final String id;
    private final String navType;
    private final String cssInitialDownload;
    private final Map<String, String> cssDownloadFilter;
    private final String cssDownloadTask;
    private DownloadTasks extraTasks;

    public DownloadInfo(String id, String navType, String cssInitialDownload,
                        Map<String, String> cssDownloadFilter,
                        String cssDownloadTask, DownloadTasks extraTasks) {
        this.id = id;
        this.navType = navType;
        this.cssInitialDownload = cssInitialDownload;
        this.cssDownloadFilter = cssDownloadFilter;
        this.cssDownloadTask = cssDownloadTask;
        this.extraTasks = extraTasks;
    }

    public String getId() {
        return id;
    }

    public String getNavType() {
        return navType;
    }

    public String getCssInitialDownload() {
        return cssInitialDownload;
    }

    public Map<String, String> getCssDownloadFilter() {
        return cssDownloadFilter;
    }

    public String getCssDownloadTask() {
        return cssDownloadTask;
    }

    public DownloadTasks getExtraTasks() {
        return extraTasks;
    }

    public void setExtraTasks(DownloadTasks extraTasks) {
        this.extraTasks = extraTasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DownloadInfo that = (DownloadInfo) o;
        return id.equals(that.id) && navType.equals(that.navType) && cssInitialDownload.equals(that.cssInitialDownload) && Objects.equals(cssDownloadFilter, that.cssDownloadFilter) && Objects.equals(cssDownloadTask, that.cssDownloadTask);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, navType, cssInitialDownload, cssDownloadFilter, cssDownloadTask);
    }

    static class Builder {
        private String id;
        private String navType;
        private String cssInitialDownload;
        private Map<String, String> cssDownloadFilter;
        private String cssDownloadTask;
        private DownloadTasks extraTasks;

        public Builder() {
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setNavType(String navType) {
            this.navType = navType;
            return this;
        }

        public Builder setCssInitialDownload(String cssInitialDownload) {
            this.cssInitialDownload = cssInitialDownload;
            return this;
        }

        public Builder setCssDownloadFilter(Map<String, String> cssDownloadFilter) {
            this.cssDownloadFilter = cssDownloadFilter;
            return this;
        }

        public Builder setCssDownloadTask(String cssDownloadTask) {
            this.cssDownloadTask = cssDownloadTask;
            return this;
        }

        public Builder setExtraTasks(DownloadTasks extraTasks) {
            this.extraTasks = extraTasks;
            return this;
        }

        public DownloadInfo build() {
            return new DownloadInfo(this.id, this.navType, this.cssInitialDownload,
                    this.cssDownloadFilter,
                    this.cssDownloadTask, this.extraTasks);
        }
    }
}