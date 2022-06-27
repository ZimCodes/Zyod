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
     * @param idDriver {@link RemoteWebDriver} used for quick OD identification
     * @param driver   {@link RemoteWebDriver} used for primary navigation
     * @param args     {@link Args}
     * @return the appropriate Navigator for the current OD
     */
    public static Navigator getNavigator(RemoteWebDriver idDriver, RemoteWebDriver driver, Args args) {
        if (new GOIndexID(idDriver).isOD()) {
            return new GoIndexNav(driver, args);
        } else if (new FODIID(idDriver).isOD()) {
            return new FODINav(driver, args);
        } else if (new ZFileID(idDriver).isOD()) {
            return new ZFileNav(driver, args);
        } else if (new GDIndexID(idDriver).isOD()) {
            return new GDIndexNav(driver, args);
        } else if (new OneDriveVercelIndexID(idDriver).isOD()) {
            return new OneDriveVercelIndexNav(driver, args);
        } else if (new GONEListID(idDriver).isOD()) {
            return new GONEListNav(driver, args);
        } else if (new ShareListID(idDriver).isOD()) {
            return new ShareListNav(driver, args);
        } else if (new YukiDriveID(idDriver).isOD()) {
            return new YukiDriveNav(driver, args);
        } else if (new AListID(idDriver).isOD()) {
            return new AListNav(driver, args);
        } else if (new WatchlistOnFireID(idDriver).isOD()) {
            return new WatchListOnFireNav(driver, args);
        } else {
            return null;
        }
    }

    /**
     * Using the identifier, create the appropriate {@link Navigator} for the current OD.
     *
     * @param driver {@link RemoteWebDriver} used for identification and primary navigation
     * @param args   {@link Args}
     * @return the appropriate Navigator for the current OD
     */
    public static Navigator getNavigator(RemoteWebDriver driver, Args args) {
        return getNavigator(driver, driver, args);
    }
}