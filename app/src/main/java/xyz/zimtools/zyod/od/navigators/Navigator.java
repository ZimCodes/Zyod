package xyz.zimtools.zyod.od.navigators;

import xyz.zimtools.zyod.assets.Directory;
import xyz.zimtools.zyod.assets.ODUrl;

import java.util.List;

public interface Navigator {
    void navigate(Directory directory);

    List<Directory> getDirResults();

    List<ODUrl> getFileResults();

    String getId();
}