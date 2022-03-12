package xyz.zimtools.zyod.args;


import com.beust.jcommander.Parameter;
import com.beust.jcommander.validators.PositiveInteger;

import java.util.Random;

public final class ArgsNavigator {
    @Parameter(names = {"-d", "--depth"}, description = "Specify the maximum depth for recursive " +
            "scraping. Depth of '1' is current directory.", validateWith = PositiveInteger.class)
    private int depth = 20;

    @Parameter(names = {"-w", "--wait"}, description = "Wait a maximum number of seconds before " +
            "scraping.", validateWith = PositiveInteger.class)
    private int wait;

    @Parameter(names = "--random-wait", description = "Randomize the amount of time to wait " +
            "before scraping.")
    private boolean isRandomWait;

    public int getDepth() {
        return this.depth;
    }

    public int getWait() {
        int result = this.wait;
        if (this.wait > 0 && this.isRandomWait) {
            Random rand = new Random();
            result = rand.doubles(this.wait * 0.5,this.wait * 1.5)
                    .mapToInt(dval -> (int)dval)
                    .findFirst()
                    .getAsInt();
        }
        return result;
    }

    public boolean isRandomWait() {
        return this.isRandomWait;
    }
}