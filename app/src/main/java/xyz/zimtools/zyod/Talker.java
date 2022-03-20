package xyz.zimtools.zyod;

/**
 * Prints messages to the console
 */
public final class Talker {

    public static void arrowHeaderInfo(String title, String info, boolean newLine) {
        Talker.println(String.format("%s: %n" +
                "--->| %s |<---", title, info), newLine);
    }

    public static void arrowInfo(String title, String info, boolean newLine) {
        Talker.println(String.format("%s --> %s", title, info), newLine);
    }

    private static void println(String message, boolean newLine) {
        if (newLine) {
            message += "\n";
        }
        System.out.println(message);
    }

    public static void loading(String title, boolean newLine) {
        String dots = ".....";
        String message = title + dots;
        println(message, newLine);
    }

    public static void complete(String title, boolean newLine) {
        String stars = "***";
        String message = String.format("%1s %2s %1s", stars, title, stars);
        println(message, newLine);
    }

    public static void warning(String title, boolean newLine) {
        String message = String.format("!- %s -!", title);
        println(message, newLine);
    }

    public static void divider() {
        String message = "=================================================";
        println(message, true);
    }

}