package xyz.zimtools.zyod.fixtures;

import xyz.zimtools.zyod.AppConfig;

import java.io.File;

public final class DownloadDefault {
    public final static String DOWNLOAD_DIR = GlobalDefault.RES_DIR + AppConfig.SEP + "DLTest";

    public static void cleanDownloadDir() {
        File directory = new File(DownloadDefault.DOWNLOAD_DIR);
        if (!directory.exists())
            return;

        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            file.delete();
        }
    }

}