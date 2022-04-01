package xyz.zimtools.zyod.args;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.validators.PositiveInteger;
import xyz.zimtools.zyod.args.converters.MillisecondConverter;

public final class ArgsMisc {
    @Parameter(names = {"--no-refresh"}, description = "Refresh the page and try again upon" +
            " a scrape failure.")
    private boolean dontRefresh;

    @Parameter(names = "--page-wait", description = "Amount of seconds to wait for a page load to" +
            " complete before throwing an error.", validateWith = PositiveInteger.class,
            converter = MillisecondConverter.class)
    private Long pageWait = 30000L;

    @Parameter(names = "--element-wait", description = "Implicitly wait a certain amount of " +
            "seconds for a web element to appear.", validateWith = PositiveInteger.class,
            converter = MillisecondConverter.class)
    private Long implicitWait = 30000L;

    public boolean isDontRefresh() {
        return dontRefresh;
    }

    public long getPageWait() {
        return pageWait;
    }

    public Long getImplicitWait() {
        return implicitWait;
    }
}