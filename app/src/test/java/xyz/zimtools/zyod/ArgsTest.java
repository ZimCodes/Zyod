package xyz.zimtools.zyod;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import xyz.zimtools.zyod.args.ArgsMain;

import xyz.zimtools.zyod.args.ArgsWebDriver;
import xyz.zimtools.zyod.fixtures.Assert;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Command Line Argument Tests")
class ArgsTest {
    String driverChoice = "firefox";

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

    private void mainArgs(String[] args) {
        ArgsMain main = new ArgsMain();
        this.argParse(args, main);
        Assert.boolTrue(main.verbose, "verbose");
        Assert.boolTrue(main.help, "help");
        Assert.boolTrue(main.version, "version");
    }

    private void webDriverArgs(String[] args) {
        ArgsWebDriver webDriver = new ArgsWebDriver();
        this.argParse(args, webDriver);
        Assert.eqStr(webDriver.webDriver, "firefox");
        Assert.eqStr(webDriver.driverPath.getPath(), System.getenv("PATH"));
        Assert.boolTrue(webDriver.headless, "headless");
    }


    private void multiArgs(String[] args) {
        ArgsMain main = new ArgsMain();
        ArgsWebDriver webDriver = new ArgsWebDriver();

        this.argParse(args, webDriver, main);

        Assert.boolTrue(main.verbose, "verbose");
        Assert.boolTrue(main.help, "help");
        Assert.boolTrue(main.version, "version");
        Assert.eqStr(webDriver.webDriver, driverChoice);
        Assert.eqStr(webDriver.driverPath.getPath(), System.getenv("PATH"));
        Assert.boolTrue(webDriver.headless, "headless");
    }

    @Test
    void noPositional() {
        String[] args = new String[]{"-h", "-v"};
        ArgsMain main = new ArgsMain();
        this.argParse(args, main);
        assertNull(main.urls, "Should not have a positional argument present.");
        Assert.boolTrue(main.verbose, "verbose");
        Assert.boolTrue(main.help, "help");
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
    void ArgsCheck() {
        String[] mainArgs = new String[]{"-h", "-v", "-V"};
        String[] driverArgs = new String[]{"--driver", driverChoice, "--headless"};
        assertAll("Arguments",
                () -> this.mainArgs(mainArgs),
                () -> this.webDriverArgs(driverArgs),
                () -> {
                    String[] combined = this.joinArr(new String[][]{mainArgs, driverArgs});
                    this.multiArgs(combined);
                }
        );
    }
}