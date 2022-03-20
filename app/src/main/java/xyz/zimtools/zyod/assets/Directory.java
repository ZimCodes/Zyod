package xyz.zimtools.zyod.assets;

import java.util.Objects;

/**
 * Holds information about a URL directory
 */
public final class Directory {
    private int depthLevel;
    private final ODUrl url;

    public Directory(int level, ODUrl url) {
        this.depthLevel = level;
        this.url = url;
    }

    /**
    * Sets depth level to zero.
     * */
    public void resetDepthLevel() {
        this.depthLevel = 0;
    }

    public void popPath() {
        this.depthLevel -= 1;
        this.url.popPath();
    }

    /*
     * Total number of directories found in the current URL address.
     * */
    public long totalFolders() {
        return this.url.getPath().chars().filter(charNum -> charNum == 47).count();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Directory directory = (Directory) o;
        return depthLevel == directory.depthLevel && url.equals(directory.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(depthLevel, url);
    }

    @Override
    public String toString() {
        return this.url.toString();
    }
}