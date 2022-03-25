package xyz.zimtools.zyod.scrapers.filters;

public final class OneDriveVercelIndexScrapeFilter extends GenericScrapeFilter {
    @Override
    public String apply(String s) {
        return super.apply(s).replaceFirst("\n"," ");
    }
}