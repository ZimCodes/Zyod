package xyz.zimtools.zyod.fixtures;

import xyz.zimtools.zyod.AppConfig;

import java.util.Arrays;
import java.util.List;

public final class GlobalDefault {
    public static final String RES_DIR = "src" + AppConfig.SEP + "test" + AppConfig.SEP + "resources";
    public static final String URL = "https://www.mozilla.org";

    public static String[] joinArr(String[][] arrs) {
        List<String> list = Arrays.stream(arrs).flatMap(Arrays::stream).toList();
        String[] items = new String[list.size()];
        return list.toArray(items);
    }
}