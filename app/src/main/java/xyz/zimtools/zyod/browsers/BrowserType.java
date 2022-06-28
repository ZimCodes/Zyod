package xyz.zimtools.zyod.browsers;

public enum BrowserType {
    FIREFOX,
    CHROME,
    MSEDGE;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
