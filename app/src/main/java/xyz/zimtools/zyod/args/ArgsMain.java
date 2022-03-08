package xyz.zimtools.zyod.args;


import com.beust.jcommander.Parameter;
import lombok.Getter;

import java.util.List;

@Getter
public class ArgsMain {
    @Parameter(description = "The URL(s) of the dynamic ODs. Can be used with or without the" +
            "'--input' option.")
    private List<String> urls;

    @Parameter(names = {"-h", "--help"}, description = "Prints usages information.", help = true)
    private boolean help;

    @Parameter(names = {"-v", "--verbose"}, description = "Enable verbose output.")
    private boolean verbose;

    @Parameter(names = {"-V", "--version"}, description = "Prints version information.")
    private boolean version;
}