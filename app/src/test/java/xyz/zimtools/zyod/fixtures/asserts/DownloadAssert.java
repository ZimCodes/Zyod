package xyz.zimtools.zyod.fixtures.asserts;

import xyz.zimtools.zyod.fixtures.DownloadDefault;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.File;

public final class DownloadAssert {
    public static void fileExists() {
        File directory = new File(DownloadDefault.DOWNLOAD_DIR);
        File[] files = directory.listFiles();
        assertNotNull(files, String.format("%s path does not exist!",
                DownloadDefault.DOWNLOAD_DIR));
        assertNotEquals(0, files.length, String.format("Download directory %s is empty! No files " +
                "were downloaded!", DownloadDefault.DOWNLOAD_DIR));
    }

}