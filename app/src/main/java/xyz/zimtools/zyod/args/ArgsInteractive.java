package xyz.zimtools.zyod.args;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.validators.PositiveInteger;
import xyz.zimtools.zyod.args.converters.MillisecondConverter;

public final class ArgsInteractive {
    @Parameter(names = "--scroll", description = "Enables scrolling feature. Scroll down the " +
            "page repeatedly until last element is reached.")
    private boolean scrolling;

    @Parameter(names = "--scroll-wait", description = "Amount of seconds to wait before " +
            "attempting to scroll again.", validateWith = PositiveInteger.class, converter =
            MillisecondConverter.class)
    private Long scrollWait = 4000L;

    public boolean isScrolling() {
        return this.scrolling;
    }

    public long getScrollWait() {
        return scrollWait;
    }
}