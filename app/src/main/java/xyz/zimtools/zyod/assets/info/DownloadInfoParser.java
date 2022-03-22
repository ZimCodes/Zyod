package xyz.zimtools.zyod.assets.info;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import xyz.zimtools.zyod.AppConfig;

import java.util.HashMap;
import java.util.Map;

public final class DownloadInfoParser extends ODInfoParser<DownloadInfo> {

    @Override
    protected void parse() {
        this.startParseStream(AppConfig.getDownloadInfoStream());
    }

    @Override
    protected void parseODEntry(Map.Entry<String, JsonElement> odEntry) {
        String odName = odEntry.getKey();
        JsonObject navTypeObject = odEntry.getValue().getAsJsonObject();
        for (Map.Entry<String, JsonElement> navTypeEntry : navTypeObject.entrySet()) {
            DownloadInfo.Builder infoBuilder = new DownloadInfo.Builder().setId(odName);
            parseNavType(navTypeEntry, infoBuilder);
        }
    }

    private void parseNavType(Map.Entry<String, JsonElement> navTypeEntry,
                              DownloadInfo.Builder infoBuilder) {
        String navType = navTypeEntry.getKey();
        infoBuilder.setNavType(navType);
        JsonObject infoObject = navTypeEntry.getValue().getAsJsonObject();
        for (Map.Entry<String, JsonElement> infoEntry : infoObject.entrySet()) {
            switch (infoEntry.getKey()) {
                case "initial_download" -> infoBuilder.setCssInitialDownload(infoEntry.getValue().getAsString());
                case "download_filter" -> this.parseFilters(infoEntry.getValue().getAsJsonObject(), infoBuilder);
                case "download_task_selector" -> infoBuilder.setCssDownloadTask(infoEntry.getValue().getAsString());
                default -> {
                }
            }
        }
        DownloadInfo downloadInfo = infoBuilder.build();
        this.infoMap.put(getKey(downloadInfo.getId(), downloadInfo.getNavType()), downloadInfo);
    }

    private void parseFilters(JsonObject filterObject, DownloadInfo.Builder infoBuilder) {
        Map<String, String> filterMap = new HashMap<>();
        for (Map.Entry<String, JsonElement> filterEntry : filterObject.entrySet()) {
            filterMap.put(filterEntry.getKey(), filterEntry.getValue().getAsString());
        }
        infoBuilder.setCssDownloadFilter(filterMap);
    }
}