package xyz.zimtools.zyod.download;

import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.function.Function;

@FunctionalInterface
public interface DownloadFilter extends Function<List<WebElement>, List<WebElement>> {}