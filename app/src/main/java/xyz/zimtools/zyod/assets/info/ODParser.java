package xyz.zimtools.zyod.assets.info;

public interface ODParser<T> {
    T getInfo(String id, String navType);

    String getKey(String id, String navType);
}