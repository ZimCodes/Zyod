package xyz.zimtools.zyod;

import xyz.zimtools.zyod.assets.Directory;
import xyz.zimtools.zyod.assets.ODUrl;

import java.util.List;

/**
 * Prints messages to the console
 */
public final class Talker {
    public static void fileStats(List<Directory> dirs, List<ODUrl> files) {
        Talker.header("Directories", false);
        Talker.dirListInfo(dirs, "Dir", false);
        Talker.arrowInfo("Total", String.valueOf(dirs.size()), true);
        Talker.header("Files", false);
        Talker.fileListInfo(files, "File", false);
        Talker.arrowInfo("Total", String.valueOf(files.size()), false);
        Talker.divider();
    }

    public static void currentDirectory(Directory directory, boolean isVerbose) {
        Talker.header("Current Directory", false);
        if (isVerbose) {
            Talker.arrowInfo("level", String.valueOf(directory.getDepthLevel()), false);
        }
        Talker.arrowInfo("url", directory.toString(), true);
    }

    public static void dirListInfo(List<Directory> dirs, String title, boolean newLine) {
        for (Directory l : dirs) {
            Talker.info(title, l.toString(), newLine);
        }
    }

    public static void fileListInfo(List<ODUrl> files, String title, boolean newLine) {
        for (ODUrl f : files) {
            Talker.info(title, f.toString(), newLine);
        }
    }

    public static void info(String title, String info, boolean newLine) {
        Talker.println(String.format("%s: %s", title, info), newLine);
    }

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

    public static void header(String title, boolean newLine) {
        String message = String.format("------%s------", title);
        Talker.println(message, newLine);
    }


}