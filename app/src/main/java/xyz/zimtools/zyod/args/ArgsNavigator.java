package xyz.zimtools.zyod.args;


import com.beust.jcommander.Parameter;
import com.beust.jcommander.validators.PositiveInteger;

public final class ArgsNavigator {
    @Parameter(names = {"-d", "--depth"}, description = "Specify the maximum depth for recursive " +
            "scraping. Depth of '1' is current directory.", validateWith = PositiveInteger.class)
    private int depth = 20;

    public int getDepth() {
        return this.depth;
    }
}