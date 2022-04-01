package xyz.zimtools.zyod.od.navigators;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import xyz.zimtools.zyod.AppConfig;
import xyz.zimtools.zyod.args.Args;
import xyz.zimtools.zyod.assets.Directory;
import xyz.zimtools.zyod.assets.ODUrl;
import xyz.zimtools.zyod.od.ODType;
import xyz.zimtools.zyod.support.NavSupport;

import java.util.List;
import java.util.Map;

/**
 * Navigate an OD purely through simulated interaction
 */
abstract class TouchNavigator extends NameNavigator {
    protected Directory curDir;

    public TouchNavigator(ODType id, RemoteWebDriver driver, Args args) {
        super(id, driver, args);
        this.curDir = new Directory(0, new ODUrl(driver.getCurrentUrl()));
    }

    @Override
    protected Map<String, Object> goToDirectory(Directory directory) {
        this.moveUpToDestination(directory);
        this.moveDownToDestination(directory);
        return Map.of(NOT_LOGIN_KEY, true, DIRECTORY_KEY, directory);
    }

    /**
     * Move up the directory tree before heading down to the destination directory
     *
     * @param destDir Destination {@link Directory}
     */
    protected void moveUpToDestination(Directory destDir) {
        while (!destDir.toString().contains(this.curDir.toString()) &&
                !this.curDir.toString().equals(this.driver.getCurrentUrl())) {
            long interactWait = this.args.getArgsInteractive().getInteractWait();
            AppConfig.sleep(interactWait);
            boolean isHome = this.goBackButton();
            if (isHome) {
                this.curDir = new Directory(0, new ODUrl(this.driver.getCurrentUrl()));
                break;
            }
            this.curDir.popPath();
        }
    }

    /**
     * Move down the directory tree to destination directory
     *
     * @param destDir destination {@link Directory}
     */
    protected void moveDownToDestination(Directory destDir) {
        while (this.curDir.getDepthLevel() != destDir.getDepthLevel()) {
            long interactWait = this.args.getArgsInteractive().getInteractWait();
            AppConfig.sleep(interactWait);
            List<WebElement> elements = NavSupport.getElements(this.driver,
                    this.scraper.getNavInfo().getCssFileSelector());
            for (WebElement el : elements) {
                WebElement nameElement =
                        el.findElement(By.cssSelector(this.scraper.getNavInfo().getCssFileName()));
                String fileName = nameElement.getText().strip();
                fileName = this.scraper.applyFilter(fileName);
                if (destDir.toString().contains(fileName)) {
                    el.click();
                    break;
                }
            }
            this.curDir = destDir;
        }
    }

    /**
     * Interaction with the OD to go back a page
     *
     * @return True if back at home page; false otherwise
     */
    protected abstract boolean goBackButton();
}