package xyz.zimtools.zyod.args;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.FileConverter;
import lombok.Getter;
import xyz.zimtools.zyod.args.validators.PathExistValidator;
import xyz.zimtools.zyod.args.validators.choice.WebDriverChoiceValidator;

import java.io.File;

@Getter
public class ArgsWebDriver {
    @Parameter(names = "--driver", description = "Type of webdriver to use. Choices: 'firefox'," +
            "'chrome', 'edge'.", validateWith = WebDriverChoiceValidator.class)
    private String webDriver = "firefox";

    @Parameter(names = "--driver-path", description = "Directory path of your webdriver. " +
            "(Default: %PATH%)", converter = FileConverter.class, validateWith = PathExistValidator.class)
    private File driverFile = new File(System.getenv("PATH"));

    @Parameter(names = "--headless", description = "Enable headless mode. Conflicts with " +
            "'--download'!")
    private boolean headless;

}