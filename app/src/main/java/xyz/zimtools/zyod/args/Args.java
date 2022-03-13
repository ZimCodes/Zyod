package xyz.zimtools.zyod.args;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import xyz.zimtools.zyod.Writer;

import java.util.List;

public final class Args {
    private final ArgsDownload argsDownload = new ArgsDownload();
    private final ArgsWebDriver argsWebDriver = new ArgsWebDriver();
    private final ArgsMain argsMain = new ArgsMain();
    private final ArgsMisc argsMisc = new ArgsMisc();
    private final ArgsInteractive argsInteractive = new ArgsInteractive();
    private final ArgsRecord argsRecord = new ArgsRecord();
    private final ArgsNavigator argsNavigator = new ArgsNavigator();

    public Args(String[] args) {
        this.argParse(args);
        this.argsCheck();
        this.combineUrls();
    }

    private void combineUrls() {
        if (this.argsRecord.getInputFile() != null) {
            List<String> inputUrls = Writer.readInputFile(this.argsRecord.getInputFile());
            this.argsMain.getUrls().addAll(inputUrls);
        }
    }

    private void argParse(String[] args) {
        JCommander.newBuilder()
                .addObject(new Object[]{
                        this.argsWebDriver,
                        this.argsRecord,
                        this.argsNavigator,
                        this.argsDownload,
                        this.argsInteractive,
                        this.argsMisc,
                        this.argsMain
                })
                .args(args)
                .build();
    }

    /**
     * Checks arguments for conflicts and report them
     */
    private void argsCheck() throws ParameterException {
        if (this.argsDownload.isDownloading() && this.argsWebDriver.isHeadless()) {
            throw new ParameterException("'--headless' CANNOT be used alongside '--download'!");
        }

        if (this.argsMain.getUrls().isEmpty() && this.argsRecord.getInputFile() == null) {
            throw new ParameterException("URL(s) must be present either in the command line or in" +
                    " an input file.");
        }
    }

    public ArgsDownload getArgsDownload() {
        return argsDownload;
    }

    public ArgsWebDriver getArgsWebDriver() {
        return argsWebDriver;
    }

    public ArgsMain getArgsMain() {
        return argsMain;
    }

    public ArgsMisc getArgsMisc() {
        return argsMisc;
    }

    public ArgsInteractive getArgsInteractive() {
        return argsInteractive;
    }

    public ArgsRecord getArgsRecord() {
        return argsRecord;
    }

    public ArgsNavigator getArgsNavigator() {
        return argsNavigator;
    }
}