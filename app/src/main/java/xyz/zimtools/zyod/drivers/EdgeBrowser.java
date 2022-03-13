package xyz.zimtools.zyod.drivers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import xyz.zimtools.zyod.args.Args;

import java.util.Map;

public class EdgeBrowser extends ChromiumBrowser {
    EdgeOptions options;

    public EdgeBrowser(Args args) {
        super(args);
        this.options = new EdgeOptions();
        this.setCapabilities();
        this.setPreferences();
    }

    @Override
    protected void setCapabilities() {
        super.setCapabilities();
        for (Map.Entry<String, Object> entry : this.capabilities.entrySet()) {
            this.options.setCapability(entry.getKey(), entry.getValue());
        }
    }

    @Override
    protected void setPreferences() {
        this.options.setHeadless(this.args.getArgsWebDriver().isHeadless());
        this.prefs.put("printing.headless_save_as_pdf_enabled", false);
        this.prefs.put("download.open_pdf_in_system_reader", false);
        if (this.args.getArgsDownload().isDownloading()) {
            super.setPreferences();
        }

        if (!this.prefs.isEmpty()) {
            this.options.setExperimentalOption("prefs", this.prefs);
        }
    }

    @Override
    public EdgeDriver getDriver() {
        if ("auto".equals(this.args.getArgsWebDriver().getDriverVersion())) {
            WebDriverManager.getInstance(EdgeDriver.class).setup();
        } else {
            WebDriverManager.getInstance(EdgeDriver.class).driverVersion(this.args.getArgsWebDriver().getDriverVersion()).setup();
        }
        return new EdgeDriver(this.options);
    }
}