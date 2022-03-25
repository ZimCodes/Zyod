package xyz.zimtools.zyod.scrapers.filters;

public class GenericScrapeFilter implements ScrapeFilter {
    final static String QUERY_REGEX = "\\?[a-zA-Z]+=[a-zA-Z0-9\\-&:%,/_+. ]+$";
    final static String DOUBLE_QUOTE_REGEX = "\"";

    @Override
    public String apply(String s) {
        String emptyStr = "";
        // Removes Queries & Double Quotes
        return s.replaceAll(QUERY_REGEX, emptyStr)
                .replaceAll(DOUBLE_QUOTE_REGEX, emptyStr);
    }
}