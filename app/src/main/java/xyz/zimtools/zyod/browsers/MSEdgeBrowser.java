package xyz.zimtools.zyod.browsers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import xyz.zimtools.zyod.args.Args;
import xyz.zimtools.zyod.support.NavSupport;

import java.time.Duration;
import java.util.Map;

public class MSEdgeBrowser extends ChromiumBrowser {
    EdgeOptions options;

    public MSEdgeBrowser(Args args) {
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
        this.options.setPageLoadTimeout(Duration.ofMillis(this.args.getArgsMisc().getPageWait()));
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
        this.options.setImplicitWaitTimeout(Duration.ofMillis(this.args.getArgsMisc().getImplicitWait()));
        return this.prepareDriver();
    }

    @Override
    public EdgeDriver getIDDriver() {
        this.options.setImplicitWaitTimeout(Duration.ofMillis(NavSupport.ID_WAIT_IMPLICIT));
        return this.prepareDriver();
    }

    /**
     * Make final preparations to the driver.
     * <p>
     * Preparation includes downloading the driver from the internet as well as setting the
     * appropriate driver version to match the installed browser.
     * </p>
     */
    @Override
    protected EdgeDriver prepareDriver() {
        if ("auto".equals(this.args.getArgsWebDriver().getDriverVersion())) {
            WebDriverManager.getInstance(EdgeDriver.class).setup();
        } else {
            WebDriverManager.getInstance(EdgeDriver.class).driverVersion(this.args.getArgsWebDriver().getDriverVersion()).setup();
        }
        return new EdgeDriver(this.options);
    }
}