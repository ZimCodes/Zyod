package xyz.zimtools.zyod;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class AppConfig {
    private final static Properties props;
    public static final String SEP;
    public static final String MIME_FILE;
    private static final String NAV_INFO_FILE;

    static {
        NAV_INFO_FILE = "nav_recipes.json";
        MIME_FILE = "mimes.json";
        SEP = System.getProperty("file.separator");
        props = new Properties();
        try {
            props.load(getResourceStream("app.properties"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static InputStream getMimeStream() {
        return getResourceStream(MIME_FILE);
    }

    public static InputStream getNavInfoStream() {
        return getResourceStream(NAV_INFO_FILE);
    }

    public static InputStream getResourceStream(String res) {
        return AppConfig.class.getResourceAsStream("/" + res);
    }

    public static String getAppVersion() {
        return props.getProperty("version");
    }

    public static String getFullDownloadPath() {
        return System.getProperty("user.home") + SEP + "Downloads" + SEP + props.getProperty(
                "download.dir");
    }

    public static void sleep(long waitMillis) {
        try {
            Thread.sleep(waitMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}