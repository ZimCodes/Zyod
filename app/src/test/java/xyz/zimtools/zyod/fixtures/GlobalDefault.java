package xyz.zimtools.zyod.fixtures;

import java.util.Arrays;
import java.util.List;

public final class GlobalDefault {
    public static final String URL = "https://www.mozilla.org";
    public static final String SEP = System.getProperty("file.separator");

    public static String[] joinArr(String[][] arrs) {
        List<String> list = Arrays.stream(arrs).flatMap(Arrays::stream).toList();
        String[] items = new String[list.size()];
        return list.toArray(items);
    }
}