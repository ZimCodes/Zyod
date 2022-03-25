package xyz.zimtools.zyod;

public class App {
    public static void main(String[] args) {
        try {
            String version = AppConfig.getAppVersion();
            System.out.println(version);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
