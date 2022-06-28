package xyz.zimtools.zyod.args;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.validators.PositiveInteger;
import xyz.zimtools.zyod.args.converters.MillisecondConverter;

public final class ArgsMisc {
    @Parameter(names = {"--no-refresh"}, description = "Don't refresh the page and try again upon" +
            " a scrape failure.")
    private boolean dontRefresh;
    @Parameter(names = {"--init-refresh"}, description = "Don't refresh the FIRST page.")
    private boolean initRefresh;

    @Parameter(names = "--page-wait", description = "Amount of seconds to implicitly wait for a " +
            "page load to" +
            " complete before throwing an error.", validateWith = PositiveInteger.class,
            converter = MillisecondConverter.class)
    private Long pageWait = 30000L;

    @Parameter(names = "--init-page-wait", description = "Amount of seconds to forcefully wait " +
            "after " +
            "navigating to the FIRST page.", validateWith = PositiveInteger.class, converter =
            MillisecondConverter.class)
    private Long initPageWait = 0L;

    @Parameter(names = "--element-wait", description = "Implicitly wait a certain amount of " +
            "seconds for a web element to appear.", validateWith = PositiveInteger.class,
            converter = MillisecondConverter.class)
    private Long implicitWait = 30000L;

    public boolean isDontRefresh() {
        return dontRefresh;
    }
    public boolean isInitRefresh(){
        return this.initRefresh;
    }

    public long getPageWait() {
        return pageWait;
    }
    public long getInitPageWait() {
        return this.initPageWait;
    }

    public Long getImplicitWait() {
        return implicitWait;
    }
}