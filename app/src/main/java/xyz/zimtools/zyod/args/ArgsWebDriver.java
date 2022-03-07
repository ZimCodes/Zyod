package xyz.zimtools.zyod.args;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.FileConverter;
import xyz.zimtools.zyod.args.validators.PathExistValidator;
import xyz.zimtools.zyod.args.validators.choice.WebDriverChoiceValidator;

import java.io.File;

public class ArgsWebDriver {
    @Parameter(names = "--driver", description = "Type of webdriver to use. Choices: 'firefox'," +
            "'chrome', 'edge'.", validateWith = WebDriverChoiceValidator.class)
    public String webDriver = "firefox";

    @Parameter(names = "--driver-path", description = "Directory path of your webdriver.",
            converter = FileConverter.class, validateWith = PathExistValidator.class)
    public File driverPath = new File(System.getenv("PATH"));

    @Parameter(names = "--headless", description = "Enable headless mode. Conflicts with " +
            "'--download'!")
    public boolean headless;

}