package xyz.zimtools.zyod;

import java.io.IOException;
import java.util.Properties;

public final class AppConfig {
    private final static Properties props;

    static {
        props = new Properties();
        try {
            props.load(AppConfig.class.getResourceAsStream("/app.properties"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String getAppVersion() {
        return props.getProperty("version");
    }
}