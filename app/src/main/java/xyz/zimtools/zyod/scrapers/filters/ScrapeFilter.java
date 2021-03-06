package xyz.zimtools.zyod.scrapers.filters;

import java.util.function.Function;

/**
 * Filter unwanted parts of a text retrieved from the scraper.
 */
@FunctionalInterface
public interface ScrapeFilter extends Function<String, String> {
}