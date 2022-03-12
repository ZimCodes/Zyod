package xyz.zimtools.zyod.args;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.FileConverter;
import com.beust.jcommander.validators.PositiveInteger;

import java.util.Random;

import java.io.File;

public final class ArgsDownload {
    @Parameter(names = "--download", description = "Enable downloading features. Conflicts with " +
            "'--headless'.")
    private boolean downloading;

    @Parameter(names = {"--ddir", "--download-dir"}, description = "Directory path to store " +
            "downloaded files. (Default: Downloads Folder)", converter = FileConverter.class)
    private File downloadDir = new File(System.getProperty("user.home") + System.getProperty(
            "file.separator") + "Downloads");

    @Parameter(names = {"--dwait", "--download-wait"}, description = "Wait a random amount of " +
            "seconds before downloading.", validateWith = PositiveInteger.class)
    private int downloadWait;

    public int getDownloadWait() {
        int result = 0;
        if (this.downloadWait > 0) {
            Random rand = new Random();
            result = rand.doubles(this.downloadWait * 0.5, this.downloadWait * 1.5)
                    .mapToInt(dval -> (int) dval)
                    .findFirst()
                    .getAsInt();
        }
        return result;
    }

    public boolean isDownloading() {
        return downloading;
    }

    public File getDownloadDir() {
        return downloadDir;
    }
}