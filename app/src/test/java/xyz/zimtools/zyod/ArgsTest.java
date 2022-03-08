package xyz.zimtools.zyod;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import xyz.zimtools.zyod.args.*;

import xyz.zimtools.zyod.fixtures.Assert;
import xyz.zimtools.zyod.fixtures.ArgsDefault;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Command Line Argument Tests")
class ArgsTest {

    private void argParse(String[] args, Object... obj) {
        JCommander.newBuilder()
                .addObject(obj)
                .args(args)
                .build();
    }

    private String[] joinArr(String[][] arrs) {
        List<String> list = Arrays.stream(arrs).flatMap(Arrays::stream).toList();
        String[] items = new String[list.size()];
        return list.toArray(items);
    }

    private void mainAsserts(ArgsMain main) {
        Assert.boolTrue(main.isVerbose(), "verbose");
        Assert.boolTrue(main.isHelp(), "help");
        Assert.boolTrue(main.isVersion(), "version");
    }

    private void mainArgs() {
        ArgsMain main = new ArgsMain();
        this.argParse(ArgsDefault.MAIN_ARGS, main);
        this.mainAsserts(main);
    }

    private void webDriverAsserts(ArgsWebDriver webDriver) {
        assertEquals(webDriver.getWebDriver(), "firefox");
        assertEquals(webDriver.getDriverFile().getPath(), System.getenv("PATH"));
        Assert.boolTrue(webDriver.isHeadless(), "headless");
    }

    private void webDriverArgs() {
        ArgsWebDriver webDriver = new ArgsWebDriver();
        this.argParse(ArgsDefault.WEB_DRIVER_ARGS, webDriver);
        this.webDriverAsserts(webDriver);
    }

    private void navigatorAsserts(ArgsNavigator navigator) {
        Assert.boolTrue(navigator.isRandomWait(), "random wait");
        assertEquals(navigator.getDepth(), ArgsDefault.DEPTH_VALUE);
    }

    private void navigatorArgs() {
        ArgsNavigator navigator = new ArgsNavigator();
        this.argParse(ArgsDefault.NAVIGATOR_ARGS, navigator);
        this.navigatorAsserts(navigator);
    }

    private void downloadAsserts(ArgsDownload download) {
        Assert.boolTrue(download.isCanDownload(), "download");
        assertEquals(download.getDownloadDir().getPath(),
                System.getProperty("user.home") + System.getProperty(
                        "file.separator") + "Downloads");
    }

    private void downloadArgs() {
        ArgsDownload download = new ArgsDownload();
        this.argParse(ArgsDefault.DOWNLOAD_ARGS, download);
        this.downloadAsserts(download);
    }

    private void recordAsserts(ArgsRecord record) {
        Assert.boolTrue(record.isDontRecord(), "no record");
        assertEquals(record.getInputFile().getPath(), ArgsDefault.INPUT_FILE);
        assertEquals(record.getOutputFile().getPath(), "output.txt");
    }

    private void recordArgs() {
        ArgsRecord record = new ArgsRecord();
        this.argParse(ArgsDefault.RECORD_ARGS, record);
        this.recordAsserts(record);
    }

    private void interactiveAsserts(ArgsInteractive interactive) {
        Assert.boolTrue(interactive.isCanScroll(), "scroll");
        assertEquals(interactive.getScrollWait(), ArgsDefault.WAIT_VALUE);
    }

    private void interactiveArgs() {
        ArgsInteractive interactive = new ArgsInteractive();
        this.argParse(ArgsDefault.INTERACTIVE_ARGS, interactive);
        this.interactiveAsserts(interactive);
    }

    private void miscAsserts(ArgsMisc misc) {
        Assert.boolTrue(misc.isCanRefresh(), "refresh");
        assertEquals(misc.getLoadWait(), ArgsDefault.WAIT_VALUE);
        assertEquals(misc.getWebWait(), ArgsDefault.WAIT_VALUE);
    }

    private void miscArgs() {
        ArgsMisc misc = new ArgsMisc();
        this.argParse(ArgsDefault.MISC_ARGS, misc);
        this.miscAsserts(misc);
    }

    private void multiArgs(String[] args) {
        ArgsMain main = new ArgsMain();
        ArgsWebDriver webDriver = new ArgsWebDriver();
        ArgsNavigator navigator = new ArgsNavigator();
        ArgsDownload download = new ArgsDownload();
        ArgsRecord record = new ArgsRecord();
        ArgsInteractive interactive = new ArgsInteractive();
        ArgsMisc misc = new ArgsMisc();

        this.argParse(args, webDriver, main, navigator, download, record, interactive, misc);

        this.mainAsserts(main);
        this.webDriverAsserts(webDriver);
        this.downloadAsserts(download);
        this.navigatorAsserts(navigator);
        this.recordAsserts(record);
        this.interactiveAsserts(interactive);
        this.miscAsserts(misc);
    }

    @Test
    void ArgsCheck() {
        assertAll("Arguments",
                this::mainArgs,
                this::webDriverArgs,
                this::navigatorArgs,
                this::downloadArgs,
                this::recordArgs,
                this::interactiveArgs,
                this::miscArgs,
                () -> {
                    String[] combined = this.joinArr(new String[][]{
                            ArgsDefault.MAIN_ARGS,
                            ArgsDefault.WEB_DRIVER_ARGS,
                            ArgsDefault.NAVIGATOR_ARGS,
                            ArgsDefault.DOWNLOAD_ARGS,
                            ArgsDefault.RECORD_ARGS,
                            ArgsDefault.INTERACTIVE_ARGS,
                            ArgsDefault.MISC_ARGS
                    });
                    this.multiArgs(combined);
                }
        );
    }

    @Test
    void noPositional() {
        ArgsMain main = new ArgsMain();
        this.argParse(ArgsDefault.MAIN_ARGS, main);
        assertNull(main.getUrls(), "Should not have a positional argument present.");
        this.mainAsserts(main);
    }

    @Test
    @DisplayName("Incorrect Driver Choice")
    void incorrectChoice() {
        String incorrectDriverChoice = "safari";
        String[] incorrectArgs = new String[]{"--driver", incorrectDriverChoice};
        ArgsWebDriver webDriver = new ArgsWebDriver();
        assertThrows(ParameterException.class, () -> this.argParse(incorrectArgs, webDriver));
    }

    @Test
    void positiveValuesOnly() {
        String negativeStr = "-3";
        String[] args = new String[]{"-w", negativeStr, "-d", negativeStr, "--dwait",
                negativeStr, "--scroll-wait", negativeStr, "--load-wait", negativeStr, "--web-wait",
                negativeStr};
        ArgsNavigator navigator = new ArgsNavigator();
        ArgsDownload download = new ArgsDownload();
        ArgsInteractive interactive = new ArgsInteractive();
        ArgsMisc misc = new ArgsMisc();
        assertThrows(ParameterException.class, () -> this.argParse(args, navigator, download,
                interactive, misc));
    }

    @RepeatedTest(10)
    void randomValueGeneration() {
        String waitStr = String.valueOf(ArgsDefault.WAIT_VALUE);
        String[] args = new String[]{"--dwait", waitStr, "-w", waitStr, "--random-wait"};
        ArgsDownload download = new ArgsDownload();
        ArgsNavigator navigator = new ArgsNavigator();
        this.argParse(args, download, navigator);
        int dWaitValue = download.getDownloadWait();
        int waitValue = navigator.getWait();

        Assert.isPositive(dWaitValue, "download wait");
        Assert.isPositive(waitValue, "wait");
    }

    @Test
    void filePathExists() {
        ArgsRecord record = new ArgsRecord();
        ArgsWebDriver webDriver = new ArgsWebDriver();
        String[] args = new String[]{"-i", ArgsDefault.INPUT_FILE, "--driver-path",
                ArgsDefault.RES_DIR};
        assertDoesNotThrow(() -> this.argParse(args, record, webDriver));
    }
}