package xyz.zimtools.zyod.od.navigators;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import xyz.zimtools.zyod.args.Args;
import xyz.zimtools.zyod.assets.info.NavInfo;
import xyz.zimtools.zyod.od.ODType;
import xyz.zimtools.zyod.support.NavSupport;

import java.util.List;

/**
 * Navigate an OD using the file/directory's name (NavInfo CSS FileName).
 * */
abstract class NameNavigator extends ODNavigator {

    public NameNavigator(ODType id, RemoteWebDriver driver, Args args) {
        super(id, driver, args);
    }

    /**
     * Retrieve {@link WebElement}s to determine if a navigational instruction is appropriate for
     * the OD.
     *
     * @param navInfo {@link NavInfo}
     * @return a list of WebElements or empty
     */
    @Override
    protected List<WebElement> getNavInfoElements(NavInfo navInfo) {
        return NavSupport.getElements(this.driver, navInfo.getCssFileName());
    }
}