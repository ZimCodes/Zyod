package xyz.zimtools.zyod.args.converters;

import com.beust.jcommander.IStringConverter;

public class MillisecondConverter implements IStringConverter<Long> {

    @Override
    public Long convert(String value) {
        return (long)(Double.parseDouble(value) * 1000);
    }
}