package xyz.zimtools.zyod.args;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.FileConverter;
import com.beust.jcommander.validators.PositiveInteger;
import xyz.zimtools.zyod.AppConfig;
import xyz.zimtools.zyod.args.converters.MillisecondConverter;

import java.util.Random;

import java.io.File;

public final class ArgsDownload {
    @Parameter(names = "--download", description = "Enable downloading features. Conflicts with " +
            "'--headless'.")
    private boolean downloading;

    @Parameter(names = {"--ddir", "--download-dir"}, description = "Directory path to store " +
            "downloaded files. (Default: Downloads Folder/Zyod)", converter = FileConverter.class)
    private File downloadDir = new File(AppConfig.getFullDownloadPath());

    @Parameter(names = {"--dwait", "--download-wait"}, description = "Wait a random amount of " +
            "seconds before downloading.", validateWith = PositiveInteger.class, converter =
            MillisecondConverter.class)
    private long downloadWait;

    public long getDownloadWait() {
        long result = 0;
        if (this.downloadWait > 0) {
            Random rand = new Random();
            result = rand.doubles(this.downloadWait * 0.5, this.downloadWait * 1.5)
                    .mapToLong(dval -> (long) dval)
                    .findFirst()
                    .getAsLong();
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