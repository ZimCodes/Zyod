package xyz.zimtools.zyod.scrapers.filters;

public class GenericScrapeFilter implements ScrapeFilter {
    final static String QUERY_REGEX = "\\?((([^=/]+=[^=]+)+)|([a-z]+))$";
    final static String DOUBLE_QUOTE_REGEX = "\"";

    @Override
    public String apply(String s) {
        String emptyStr = "";
        // Removes Queries & Double Quotes
        //NOTE: Removing Queries may also remove the fragment part '#fragment' too.
        return s.replaceAll(QUERY_REGEX, emptyStr)
                .replaceAll(DOUBLE_QUOTE_REGEX, emptyStr);
    }
}