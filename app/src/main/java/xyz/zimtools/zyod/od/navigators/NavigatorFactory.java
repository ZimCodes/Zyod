package xyz.zimtools.zyod.od.navigators;

import org.openqa.selenium.remote.RemoteWebDriver;
import xyz.zimtools.zyod.args.Args;
import xyz.zimtools.zyod.od.identifiers.*;

/**
 * Factory for creating the appropriate navigator to use for an OD
 */
public final class NavigatorFactory {

    /**
     * Using the identifier, create the appropriate {@link Navigator} for the current OD.
     *
     * @param driver {@link RemoteWebDriver}
     * @param args   {@link Args}
     * @return the appropriate Navigator for the current OD
     */
    public static Navigator getNavigator(RemoteWebDriver driver, Args args) {
        if (new GOIndexID(driver).isOD()) {
            return new GoIndexNav(driver, args);
        } else if (new FODIID(driver).isOD()) {
            return new FODINav(driver, args);
        } else if (new ZFileID(driver).isOD()) {
            return new ZFileNav(driver, args);
        } else if (new GDIndexID(driver).isOD()) {
            return new GDIndexNav(driver, args);
        } else if (new OneDriveVercelIndexID(driver).isOD()) {
            return new OneDriveVercelIndexNav(driver, args);
        } else if (new GONEListID(driver).isOD()) {
            return new GONEListNav(driver, args);
        } else if (new ShareListID(driver).isOD()) {
            return new ShareListNav(driver, args);
        } else if (new YukiDriveID(driver).isOD()) {
            return new YukiDriveNav(driver, args);
        } else if (new AListID(driver).isOD()) {
            return new AListNav(driver, args);
        } else if (new WatchlistOnFireID(driver).isOD()) {
            return new WatchListOnFireNav(driver, args);
        } else {
            return null;
        }
    }
}