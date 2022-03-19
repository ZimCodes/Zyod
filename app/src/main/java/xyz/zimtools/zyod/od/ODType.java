package xyz.zimtools.zyod.od;

public enum ODType {
    GENERIC("Generic"),
    GOINDEX("GoIndex"),
    FODI("FODI"),
    ZFILE("ZFile"),
    GDINDEX("GDIndex"),
    ONEDRIVE_VERCEL_INDEX("onedrive-vercel-index"),
    GONELIST("GONEList"),
    SHARELIST("ShareList"),
    YUKIDRIVE("Yuki Drive"),
    ALIST("AList"),
    WATCHLIST_ON_FIRE("Watchlist on fire 🔥🔥🔥");
    private final String odName;

    ODType(String odName) {
        this.odName = odName;
    }

    @Override
    public String toString() {
        return this.odName;
    }
}