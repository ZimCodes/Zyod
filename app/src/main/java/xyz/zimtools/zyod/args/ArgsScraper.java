package xyz.zimtools.zyod.args;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.validators.PositiveInteger;
import xyz.zimtools.zyod.args.converters.MillisecondConverter;

import java.util.Random;

public final class ArgsScraper {
    @Parameter(names = {"-w", "--wait"}, description = "Wait a number of seconds before " +
            "scraping.", validateWith = PositiveInteger.class, converter =
            MillisecondConverter.class)
    private Long wait = 6000L;

    @Parameter(names = "--random-wait", description = "Randomize the amount of time to wait " +
            "before scraping. Used with '--wait,-w'")
    private boolean isRandomWait;

    public long getWait() {
        long result = this.wait;
        if (result > 0 && this.isRandomWait) {
            Random rand = new Random();
            result = rand.doubles(this.wait * 0.5, this.wait * 1.5)
                    .mapToLong(dval -> (long) dval)
                    .findFirst()
                    .getAsLong();
        }
        return result;
    }

    public boolean isRandomWait() {
        return this.isRandomWait;
    }
}