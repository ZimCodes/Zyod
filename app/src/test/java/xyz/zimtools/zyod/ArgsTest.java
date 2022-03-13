package xyz.zimtools.zyod;

import com.beust.jcommander.ParameterException;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import xyz.zimtools.zyod.args.*;

import xyz.zimtools.zyod.fixtures.Assert;
import xyz.zimtools.zyod.fixtures.ArgsDefault;
import xyz.zimtools.zyod.fixtures.GlobalDefault;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests parsing of all command line arguments
 */
@DisplayName("Command Line Argument Tests")
class ArgsTest {

    private void urlAssert(String[] args) {
        assertDoesNotThrow(() -> new Args(args), "URL(s) may not have been present.");
    }

    private void mainAsserts(Args args) {
        Assert.argTrue(args.getArgsMain().isVerbose(), "verbose");
        Assert.argTrue(args.getArgsMain().isHelp(), "help");
        Assert.argTrue(args.getArgsMain().isVersion(), "version");
    }

    private void mainArgs() {
        this.urlAssert(ArgsDefault.MAIN_ARGS);
        Args args = new Args(ArgsDefault.MAIN_ARGS);
        this.mainAsserts(args);
    }

    private void webDriverAsserts(Args args) {
        assertEquals(args.getArgsWebDriver().getDriverName(), "firefox", "The chosen driver name " +
                "is incorrect.");
        Assert.argTrue(args.getArgsWebDriver().isAllCerts(), "all certs");
        assertEquals(args.getArgsWebDriver().getDriverVersion(), "auto", "Driver versions do not match.");
    }

    private void webDriverArgs() {
        this.urlAssert(ArgsDefault.WEB_DRIVER_ARGS);
        Args args = new Args(ArgsDefault.WEB_DRIVER_ARGS);
        Assert.argTrue(args.getArgsWebDriver().isHeadless(), "headless");
        this.webDriverAsserts(args);
    }

    private void navigatorAsserts(Args args) {
        Assert.argTrue(args.getArgsNavigator().isRandomWait(), "random wait");
        assertEquals(args.getArgsNavigator().getDepth(), ArgsDefault.DEPTH_VALUE);
    }

    private void navigatorArgs() {
        this.urlAssert(ArgsDefault.NAVIGATOR_ARGS);
        Args args = new Args(ArgsDefault.NAVIGATOR_ARGS);
        this.navigatorAsserts(args);
    }

    private void downloadAsserts(Args args) {
        Assert.argTrue(args.getArgsDownload().isDownloading(), "download");
        assertEquals(args.getArgsDownload().getDownloadDir().getPath(), ArgsDefault.DOWNLOAD_DIR);
    }

    private void downloadArgs() {
        this.urlAssert(ArgsDefault.DOWNLOAD_ARGS);
        Args args = new Args(ArgsDefault.DOWNLOAD_ARGS);
        this.downloadAsserts(args);
    }

    private void recordAsserts(Args args) {
        Assert.argTrue(args.getArgsRecord().isNotRecording(), "no args.getArgsRecord");
        assertEquals(args.getArgsRecord().getInputFile().getPath(), ArgsDefault.INPUT_FILE);
        assertEquals(args.getArgsRecord().getOutputFile().getPath(), "output.txt");
    }

    private void recordArgs() {
        this.urlAssert(ArgsDefault.RECORD_ARGS);
        Args args = new Args(ArgsDefault.RECORD_ARGS);
        this.recordAsserts(args);
    }

    private void interactiveAsserts(Args args) {
        Assert.argTrue(args.getArgsInteractive().isScrolling(), "scroll");
        assertEquals(args.getArgsInteractive().getScrollWait(), ArgsDefault.WAIT_VALUE);
    }

    private void interactiveArgs() {
        this.urlAssert(ArgsDefault.INTERACTIVE_ARGS);
        Args args = new Args(ArgsDefault.INTERACTIVE_ARGS);
        this.interactiveAsserts(args);
    }

    private void miscAsserts(Args args) {
        Assert.argTrue(args.getArgsMisc().isRefreshing(), "refresh");
        assertEquals(args.getArgsMisc().getLoadWait(), ArgsDefault.WAIT_VALUE);
        assertEquals(args.getArgsMisc().getWebWait(), ArgsDefault.WAIT_VALUE);

    }

    private void miscArgs() {
        this.urlAssert(ArgsDefault.MISC_ARGS);
        Args args = new Args(ArgsDefault.MISC_ARGS);
        this.miscAsserts(args);
    }

    private void multiArgs(String[] args) {
        this.urlAssert(args);
        Args argsObj = new Args(args);
        this.mainAsserts(argsObj);
        this.webDriverAsserts(argsObj);
        this.downloadAsserts(argsObj);
        this.navigatorAsserts(argsObj);
        this.recordAsserts(argsObj);
        this.interactiveAsserts(argsObj);
        this.miscAsserts(argsObj);
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
                    String[] combined = GlobalDefault.joinArr(new String[][]{
                            ArgsDefault.MAIN_ARGS,
                            ArgsDefault.WEB_DRIVER_NO_HEADLESS_ARGS,
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

    /**
     * Test to check if a URL has not been received.
     */
    @Test
    void noURLs() {
        String[] args = new String[]{"-h", "-v", "-V"};
        assertThrows(ParameterException.class, () -> new Args(args),
                "Should not have a positional argument and '--input' option present.");
    }

    @Test
    @DisplayName("Positional Argument")
    void positional() {
        this.urlAssert(ArgsDefault.MAIN_ARGS);
        Args args = new Args(ArgsDefault.MAIN_ARGS);
        assertFalse(args.getArgsMain().getUrls().isEmpty(), "Should have a positional argument " +
                "present.");
        this.mainAsserts(args);
    }

    /**
     * Test if input file and url in command line are combined
     */
    @Test
    @DisplayName("Positional & Input URLs")
    void positionalInput() {
        Args argObj = new Args(ArgsDefault.URLS_ARGS);
        assertEquals(4, argObj.getArgsMain().getUrls().size(), "There should be a total of 4 " +
                "urls total for Zyod to navigate.");
    }

    @Test
    @DisplayName("Incorrect Driver Choice")
    void incorrectChoice() {
        String incorrectDriverChoice = "safari";
        String[] incorrectArgs = new String[]{"--driver", incorrectDriverChoice};
        assertThrows(ParameterException.class, () -> new Args(incorrectArgs));
    }

    @Test
    void positiveValuesOnly() {
        assertThrows(ParameterException.class, () -> new Args(ArgsDefault.NEGATIVE_WAIT_ARGS));
    }

    @RepeatedTest(10)
    void randomValueGeneration() {
        this.urlAssert(ArgsDefault.RAND_WAIT_ARGS);
        Args argObj = new Args(ArgsDefault.RAND_WAIT_ARGS);

        int dWaitValue = argObj.getArgsDownload().getDownloadWait();
        int waitValue = argObj.getArgsNavigator().getWait();

        Assert.isPositive(dWaitValue, "download wait");
        Assert.isPositive(waitValue, "wait");
    }

    @Test
    void filePathExists() {
        String[] args = new String[]{"-i", ArgsDefault.INPUT_FILE};
        assertDoesNotThrow(() -> new Args(args));
    }

    @Test
    void ArgConflicts() {
        String[] args = new String[]{"--headless", "--download"};
        assertThrows(ParameterException.class, () -> new Args(args));
    }
}