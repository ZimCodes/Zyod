package xyz.zimtools.zyod.od.navigators;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import xyz.zimtools.zyod.assets.NavInfo;
import xyz.zimtools.zyod.od.ODType;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests if {@link NavInfo} is appropriately retrieved & parsed.
 */
class NavInfoTest {
    private static NavInfoParser parser;

    @BeforeAll
    static void init() {
        parser = new NavInfoParser();
    }

    @Test
    void alistOriginal() {
        this.infoExists(ODType.ALIST.name(), NavType.AList.ORIGINAL.name());
    }

    @Test
    void alistWeb() {
        this.infoExists(ODType.ALIST.name(), NavType.AList.WEB.name());
    }

    @Test
    void gdIndexMain() {
        this.infoExists(ODType.GDINDEX.name(), NavType.GDIndex.MAIN.name());
    }

    @Test
    void fodiMain() {
        this.infoExists(ODType.FODI.name(), NavType.FODI.MAIN.name());
    }

    @Test
    void goIndexListView() {
        this.infoExists(ODType.GOINDEX.name(), NavType.GoIndex.LIST_VIEW.name());
    }

    @Test
    void goIndexThumbnailView() {
        this.infoExists(ODType.GOINDEX.name(), NavType.GoIndex.THUMBNAIL_VIEW.name());
    }

    @Test
    void goIndexOlder() {
        this.infoExists(ODType.GOINDEX.name(), NavType.GoIndex.OLDER.name());
    }

    @Test
    void gonelistMain() {
        this.infoExists(ODType.GONELIST.name(), NavType.GONEList.MAIN.name());
    }

    @Test
    void onedriveVercelIndexMain() {
        this.infoExists(ODType.ONEDRIVE_VERCEL_INDEX.name(), NavType.Onedrive_Vercel_Index.MAIN.name());
    }

    @Test
    void sharelistInteractive() {
        this.infoExists(ODType.SHARELIST.name(), NavType.ShareList.INTERACTIVE.name());
    }

    @Test
    void sharelistDownloadQuery() {
        this.infoExists(ODType.SHARELIST.name(), NavType.ShareList.DOWNLOAD_QUERY.name());
    }

    @Test
    void sharelistPreviewQuery() {
        this.infoExists(ODType.SHARELIST.name(), NavType.ShareList.PREVIEW_QUERY.name());
    }

    @Test
    void watchListOnFireMain() {
        this.infoExists(ODType.WATCHLIST_ON_FIRE.name(), NavType.WatchList_On_Fire.MAIN.name());
    }

    @Test
    void yukiDriveMain() {
        this.infoExists(ODType.YUKIDRIVE.name(), NavType.YukiDrive.MAIN.name());
    }

    @Test
    void zfileMain() {
        this.infoExists(ODType.ZFILE.name(), NavType.ZFile.MAIN.name());
    }


    private void infoExists(String od, String navType) {
        NavInfo info = parser.getNavInfo(od, navType);
        assertNotNull(info, String.format("OD type '%s' with navigation type '%s' does not " +
                "exist!", od, navType));
    }
}