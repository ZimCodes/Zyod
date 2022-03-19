package xyz.zimtools.zyod.od.navigators;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import xyz.zimtools.zyod.AppConfig;
import xyz.zimtools.zyod.assets.NavInfo;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class NavInfoParser {
    private final Map<String, NavInfo> navInfoMap;


    public NavInfoParser() {
        this.navInfoMap = new HashMap<>();
        initNavInfos();
    }

    public NavInfo getNavInfo(String id, String navType) {
        return this.navInfoMap.get(getKey(id, navType));
    }

    private void initNavInfos() {
        try (InputStream inputStream = AppConfig.getNavInfoStream();
             InputStreamReader reader = new InputStreamReader(inputStream)) {
            JsonElement element = JsonParser.parseReader(reader);
            JsonObject object = element.getAsJsonObject();
            for (Map.Entry<String, JsonElement> odElement : object.entrySet()) {
                parseODEntry(odElement);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parses JSON for OD type.
     *
     * @param odEntry JSON entries for OD types
     */
    private void parseODEntry(Map.Entry<String, JsonElement> odEntry) {
        String odName = odEntry.getKey();
        JsonObject navTypeObject = odEntry.getValue().getAsJsonObject();
        for (Map.Entry<String, JsonElement> navTypeEntry : navTypeObject.entrySet()) {
            NavInfo.Builder infoBuilder = new NavInfo.Builder().setId(odName);
            parseNavType(navTypeEntry, infoBuilder);
        }
    }

    /**
     * Parses JSON for the OD Navigation Type.
     *
     * @param odTypeEntry JSON entries for Navigation Types
     * @param infoBuilder {@link NavInfo.Builder}
     */
    private void parseNavType(Map.Entry<String, JsonElement> odTypeEntry,
                              NavInfo.Builder infoBuilder) {
        String odType = odTypeEntry.getKey();
        infoBuilder.setNavType(odType);
        JsonObject infoObject = odTypeEntry.getValue().getAsJsonObject();
        for (Map.Entry<String, JsonElement> infoEntry : infoObject.entrySet()) {
            String infoValue = infoEntry.getValue().getAsString();
            switch (infoEntry.getKey()) {
                case "file_selector" -> infoBuilder.setCssFileSelector(infoValue);
                case "file_name" -> infoBuilder.setCssFileName(infoValue);
                case "attribute" -> infoBuilder.setCssAttr(infoValue);
                case "initial_download" -> infoBuilder.setCssInitialDownload(infoValue);
                case "reject_filter" -> infoBuilder.setCssRejectFilter(infoValue);
                default -> {
                }
            }
        }
        NavInfo navInfo = infoBuilder.build();
        this.navInfoMap.put(getKey(navInfo.getId(), navInfo.getNavType()), navInfo);
    }

    private String getKey(String id, String navType) {
        return id.toLowerCase() + "_" + navType.toLowerCase();
    }
}