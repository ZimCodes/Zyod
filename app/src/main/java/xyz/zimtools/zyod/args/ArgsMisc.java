package xyz.zimtools.zyod.args;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.validators.PositiveInteger;
import lombok.Getter;

@Getter
public class ArgsMisc {
    @Parameter(names = "--web-wait", description = "Amount of seconds to wait for browser to " +
            "INITIALLY" +
            " load up each OD before executing Zyod.", validateWith = PositiveInteger.class)
    private int webWait = 15;

    @Parameter(names = {"-r", "--refresh"}, description = "Refresh the page and try again upon failure.")
    private boolean canRefresh;

    @Parameter(names = "--load-wait", description = "Amount of seconds to wait for a page load to" +
            " complete before throwing an error.", validateWith = PositiveInteger.class)
    private int loadWait = 30;
}