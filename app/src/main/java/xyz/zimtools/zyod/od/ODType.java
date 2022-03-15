package xyz.zimtools.zyod.od;

public enum ODType {
    GENERIC("Generic"),
    GO_INDEX("GoIndex"),
    FODI("FODI"),
    ZFILE("ZFile"),
    GD_INDEX("GDIndex"),
    ONEDRIVE_VERCEL_INDEX("onedrive-vercel-index"),
    GONELIST("GONEList"),
    SHARELIST("ShareList"),
    YUKI_DRIVE("Yuki Drive"),
    ALIST("AList"),
    WATCHLIST_ON_FIRE("Watchlist on fire 🔥🔥🔥");
    private final String id;

    ODType(String odName) {
        this.id = odName;
    }

    @Override
    public String toString() {
        return this.id;
    }
}