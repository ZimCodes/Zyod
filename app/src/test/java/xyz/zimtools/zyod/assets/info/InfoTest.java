package xyz.zimtools.zyod.assets.info;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import xyz.zimtools.zyod.fixtures.asserts.AssetInfoAssert;
import xyz.zimtools.zyod.od.ODType;
import xyz.zimtools.zyod.od.navigators.NavType;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

/**
 * Tests if {@link NavInfo} is appropriately retrieved & parsed.
 */
class InfoTest {
    @ParameterizedTest
    @MethodSource("getParser")
    void alistOriginal(ODParser parser) {
        AssetInfoAssert.infoExists(ODType.ALIST.name(), NavType.AList.ORIGINAL.name(), parser);
    }

    @ParameterizedTest
    @MethodSource("getParser")
    void alistWeb(ODParser parser) {
        AssetInfoAssert.infoExists(ODType.ALIST.name(), NavType.AList.WEB.name(), parser);
    }

    @ParameterizedTest
    @MethodSource("getParser")
    void gdIndexMain(ODParser parser) {
        AssetInfoAssert.infoExists(ODType.GDINDEX.name(), NavType.GDIndex.MAIN.name(), parser);
    }

    @ParameterizedTest
    @MethodSource("getParser")
    void fodiMain(ODParser parser) {
        AssetInfoAssert.infoExists(ODType.FODI.name(), NavType.FODI.MAIN.name(), parser);
    }

    @ParameterizedTest
    @MethodSource("getParser")
    void goIndexListView(ODParser parser) {
        AssetInfoAssert.infoExists(ODType.GOINDEX.name(), NavType.GoIndex.LIST_VIEW.name(), parser);
    }

    @ParameterizedTest
    @MethodSource("getParser")
    void goIndexThumbnailView(ODParser parser) {
        AssetInfoAssert.infoExists(ODType.GOINDEX.name(), NavType.GoIndex.THUMBNAIL_VIEW.name(),
                parser);
    }

    @ParameterizedTest
    @MethodSource("getParser")
    void goIndexOlder(ODParser parser) {
        AssetInfoAssert.infoExists(ODType.GOINDEX.name(), NavType.GoIndex.OLDER.name(), parser);
    }

    @ParameterizedTest
    @MethodSource("getParser")
    void gonelistMain(ODParser parser) {
        AssetInfoAssert.infoExists(ODType.GONELIST.name(), NavType.GONEList.MAIN.name(), parser);
    }

    @ParameterizedTest
    @MethodSource("getParser")
    void onedriveVercelIndexMain(ODParser parser) {
        AssetInfoAssert.infoExists(ODType.ONEDRIVE_VERCEL_INDEX.name(),
                NavType.Onedrive_Vercel_Index.MAIN.name(), parser);
    }

    @ParameterizedTest
    @MethodSource("getParser")
    void sharelistInteractive(ODParser parser) {
        AssetInfoAssert.infoExists(ODType.SHARELIST.name(),
                NavType.ShareList.INTERACTIVE.name(), parser);
    }

    @ParameterizedTest
    @MethodSource("getParser")
    void sharelistDownloadQuery(ODParser parser) {
        AssetInfoAssert.infoExists(ODType.SHARELIST.name(),
                NavType.ShareList.DOWNLOAD_QUERY.name(), parser);
    }

    @ParameterizedTest
    @MethodSource("getParser")
    void sharelistPreviewQuery(ODParser parser) {
        AssetInfoAssert.infoExists(ODType.SHARELIST.name(),
                NavType.ShareList.PREVIEW_QUERY.name(), parser);
    }

    @ParameterizedTest
    @MethodSource("getParser")
    void watchListOnFireMain(ODParser parser) {
        AssetInfoAssert.infoExists(ODType.WATCHLIST_ON_FIRE.name(),
                NavType.WatchList_On_Fire.MAIN.name(), parser);
    }

    @ParameterizedTest
    @MethodSource("getParser")
    void yukiDriveMain(ODParser parser) {
        AssetInfoAssert.infoExists(ODType.YUKIDRIVE.name(), NavType.YukiDrive.MAIN.name(), parser);
    }

    @ParameterizedTest
    @MethodSource("getParser")
    void zfileMain(ODParser parser) {
        AssetInfoAssert.infoExists(ODType.ZFILE.name(), NavType.ZFile.MAIN.name(), parser);
    }

    private static Stream<Arguments> getParser() {
        return Stream.of(
                Arguments.of(new NavInfoParser()),
                Arguments.of(new DownloadInfoParser())
        );
    }
}