package xyz.zimtools.zyod.assets.info;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

abstract class ODInfoParser<T> implements ODParser<T> {
    protected final Map<String, T> infoMap;

    public ODInfoParser() {
        this.infoMap = new HashMap<>();
        this.parse();
    }

    protected abstract void parse();

    protected void startParseStream(InputStream iStream) {
        try (InputStream inputStream = iStream;
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

    protected abstract void parseODEntry(Map.Entry<String, JsonElement> odEntry);

    @Override
    public T getInfo(String id, String navType) {
        return this.infoMap.get(this.getKey(id, navType));
    }

    @Override
    public String getKey(String id, String navType) {
        return id.toLowerCase() + "_" + navType.toLowerCase();
    }
}