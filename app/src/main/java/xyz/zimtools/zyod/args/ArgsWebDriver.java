package xyz.zimtools.zyod.args;

import com.beust.jcommander.Parameter;
import xyz.zimtools.zyod.args.validators.choice.WebDriverChoiceValidator;

public final class ArgsWebDriver {
    @Parameter(names = "--driver", description = "Type of webdriver to use. Choices: 'firefox'," +
            "'chrome', 'msedge'.", validateWith = WebDriverChoiceValidator.class)
    private String driverName = "firefox";

    @Parameter(names = "--headless", description = "Enable headless mode. Conflicts with " +
            "'--download'!")
    private boolean headless;

    @Parameter(names = "--all-certs", description = "Accepts all certificates even invalid ones.")
    private boolean allCerts;

    @Parameter(names = "--compat-driver", description = "The driver version to download.")
    private String driverVersion = "auto";

    public String getDriverVersion(){
        return this.driverVersion;
    }

    public String getDriverName() {
        return this.driverName;
    }

    public boolean isHeadless() {
        return headless;
    }

    public boolean isAllCerts() {
        return allCerts;
    }
}