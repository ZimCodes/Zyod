package xyz.zimtools.zyod.args;


import com.beust.jcommander.Parameter;

import java.util.List;

public class ArgsMain {
    @Parameter(description = "THe URL(s) of the dynamic ODs.")
    public List<String> urls;

    @Parameter(names = {"-h", "--help"}, description = "Prints usages information.", help = true)
    public boolean help;

    @Parameter(names = {"-v", "--verbose"}, description = "Enable verbose output.")
    public boolean verbose;

    @Parameter(names = {"-V", "--version"}, description = "Prints version information.")
    public boolean version;
}