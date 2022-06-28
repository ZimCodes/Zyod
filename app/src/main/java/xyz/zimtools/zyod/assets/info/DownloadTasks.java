package xyz.zimtools.zyod.assets.info;

import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface DownloadTasks extends BiConsumer<RemoteWebDriver, DownloadInfo> {

}