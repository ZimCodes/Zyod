package xyz.zimtools.zyod.assets.info;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import xyz.zimtools.zyod.AppConfig;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public final class NavInfoParser extends ODInfoParser<NavInfo> {

    @Override
    protected void parse() {
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
    @Override
    protected void parseODEntry(Map.Entry<String, JsonElement> odEntry) {
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
            switch (infoEntry.getKey()) {
                case "file_selector" -> infoBuilder.setCssFileSelector(infoEntry.getValue().getAsString());
                case "file_name" -> infoBuilder.setCssFileName(infoEntry.getValue().getAsString());
                case "attribute" -> infoBuilder.setCssAttr(infoEntry.getValue().getAsString());
                case "file_reject_filter" -> infoBuilder.setCssRejectFilter(infoEntry.getValue().getAsString());
                case "back" ->
                    parseBackInfo(infoEntry.getValue().getAsJsonObject(),
                            infoBuilder);
                default -> {
                }
            }
        }
        NavInfo navInfo = infoBuilder.build();
        this.infoMap.put(getKey(navInfo.getId(), navInfo.getNavType()), navInfo);
    }

    private void parseBackInfo(JsonObject backEl, NavInfo.Builder infoBuilder) {
        Map<String, String> cssBackMap = new HashMap<>();
        for (Map.Entry<String, JsonElement> backEntry : backEl.entrySet()) {
            cssBackMap.put(backEntry.getKey(), backEntry.getValue().getAsString());
        }
        infoBuilder.setCssBackMap(cssBackMap);
    }
}