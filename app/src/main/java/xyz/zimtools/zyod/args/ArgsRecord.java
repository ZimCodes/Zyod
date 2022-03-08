package xyz.zimtools.zyod.args;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.FileConverter;
import lombok.Getter;
import xyz.zimtools.zyod.args.validators.PathExistValidator;

import java.io.File;

@Getter
public class ArgsRecord {
    @Parameter(names = {"-o", "--output"}, description = "The output file path to place all recorded " +
            "links. Links are appended to the file!", converter = FileConverter.class)
    private File outputFile = new File("output.txt");

    @Parameter(names = {"-i", "--input"}, description = "Read links from a file, which points to " +
            "a series of ODs. Can be used with or without the URLs positional option.",
            converter = FileConverter.class, validateWith = PathExistValidator.class)
    private File inputFile;

    @Parameter(names = {"--no-record"}, description = "Disable recording features.")
    private boolean dontRecord;
}