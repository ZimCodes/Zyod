package xyz.zimtools.zyod.od.identifiers;

import org.openqa.selenium.remote.RemoteWebDriver;

public final class ZFileID extends ODID {
    private static final String HEADER = "文件名";

    public ZFileID(RemoteWebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isOD() {
        return this.uniqueHeader() || this.tableHeader() || this.uniqueColumn() || this.uniqueItalics();
    }

    /**
     * Search for unique header CSS Selector
     */
    private boolean uniqueHeader() {
        return this.exists(".zfile-header");
    }

    /**
     * Search for unique table column CSS Selector
     */
    private boolean uniqueColumn() {
        return this.exists(".zfile-table-col-name");
    }

    /**
     * Search for unique italics CSS Selector
     */
    private boolean uniqueItalics() {
        return this.exists("i.zfile-margin-left-5");
    }

    /**
     * Table header name column
     */
    private boolean tableHeader() {
        return this.findTextCheck("thead th div span:not(.caret-wrapper)", HEADER);
    }


}