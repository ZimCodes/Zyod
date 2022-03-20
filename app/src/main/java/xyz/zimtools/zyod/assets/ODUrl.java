package xyz.zimtools.zyod.assets;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ODUrl {
    private static final Pattern FILE_URL_REGEX = Pattern.compile("[^/=#]/[a-zA-Z0-9" +
            "~+\\-%\\[\\]$_.!'()= ]+\\.(?:[a-zA-Z0-9]{3,7}|[a-zA-Z][a-zA-Z0-9]|[0-9][a-zA-Z])$");
    private String scheme;
    private String authority;
    private String path;
    private String query;
    private String fragment;

    public ODUrl(String link) {
        URI uri;
        try {
            uri = new URI(link);
            this.scheme = uri.getScheme();
            if (this.scheme == null) {
                this.scheme = "";
            }
            this.authority = uri.getRawAuthority();
            if (this.authority == null) {
                this.authority = "";
            }
            this.path = uri.getRawPath();
            this.path = this.path == null ? "/" : addStartSlash(this.path);
            this.query = uri.getRawQuery();
            this.addQueryPathToPath();
            this.fragment = uri.getRawFragment();
            this.addRefPathToPath();
        } catch (URISyntaxException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getScheme() {
        return scheme;
    }

    public String getAuthority() {
        return authority;
    }

    public String getPath() {
        return path;
    }

    public String getQuery() {
        return query;
    }

    public String getFragment() {
        return fragment;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setFragment(String fragment) {
        this.fragment = fragment;
    }

    /**
     * Appends a new path to URL.
     *
     * @param newPath the new path to append
     */
    public void addPath(String newPath) {
        this.path = slashJoin(this.path, newPath);
    }

    /**
     * Removes the last path and returns it.
     *
     * @return the removed path from the URL.
     */
    public String popPath() {
        String[] paths = this.path.split("/");
        int limit = paths.length != 0 ? paths.length - 1 : 0;
        List<String> popList = Arrays.stream(paths).limit(limit).toList();
        this.path = addStartSlash(String.join("/", popList));
        return limit == 0 ? "" : paths[limit];
    }

    /**
     * Transform current URL into a directory URL.
     * <p>
     * The current URL, which may point to a file resource, will transform into a URL
     * pointing to a directory.
     * </p>
     * <p>
     * This method is mutable.
     * </p>
     */
    public void dirTransform() {
        this.path = addLastSlash(this.path);
    }


    /**
     * Joins two URLs together.
     *
     * @param url the current url
     * @param rel the relative url retrieved from an HTML element
     * @return {@link xyz.zimtools.zyod.assets.ODUrl}
     * @see xyz.zimtools.zyod.assets.ODUrl
     */
    public static String joiner(String url, String rel) {
        ODUrl parsedUrl = new ODUrl(url);
        ODUrl parsedRel = new ODUrl(rel);
        if (parsedUrl.equals(parsedRel)) {
            return parsedUrl.toString();
        }

        if (!parsedUrl.getPath().equals(parsedRel.getPath())) {
            if (parsedRel.getPath().contains(parsedUrl.getPath())) {
                parsedUrl.setPath(parsedRel.getPath());
            } else {
                parsedUrl.setPath(slashJoin(parsedUrl.getPath(), parsedRel.getPath()));
            }
        }

        if (!parsedUrl.getQuery().equals(parsedRel.getQuery())) {
            if (parsedRel.getQuery().contains(parsedUrl.getQuery())) {
                parsedUrl.setQuery(parsedRel.getQuery());
            } else {
                parsedUrl.setQuery(parsedUrl.getQuery() + "&" + parsedRel.getQuery());
            }
        }

        return parsedUrl.toString();
    }

    public static boolean isFile(String link) {
        Matcher fileMat = FILE_URL_REGEX.matcher(link);
        return fileMat.find();
    }

    /**
     * Adds {@code /?/} as a path and not a query
     */
    private void addQueryPathToPath() {
        if (this.query == null) {
            this.query = "";
        } else if (this.query.isEmpty()) {
            //if empty, it means a /?/ path was present
            this.path = slashJoin(this.path, "?");
        } else if (this.query.startsWith("/")) {
            this.query = slashJoin("/?/", this.query);
            this.path = slashJoin(this.path, this.query);
            this.query = "";
        }
    }

    /**
     * Adds {@code /#/} as a path and not a fragment.
     */
    private void addRefPathToPath() {
        if (this.fragment == null) {
            this.fragment = "";
        } else if (this.fragment.isEmpty()) {
            //if empty, there was a '/#/' present
            this.path = slashJoin(this.path, "#");
        } else if (this.fragment.startsWith("/")) {
            this.fragment = slashJoin("/#/", this.fragment);
            this.path = slashJoin(this.path, this.fragment);
            this.fragment = "";
        }
    }

    private static String slashJoin(String url, String rel) {
        if (url.endsWith("/") && rel.startsWith("/")) {
            return url + rel.substring(1);
        } else if (!url.endsWith("/") && !rel.startsWith("/")) {
            return url + "/" + rel;
        }
        return url + rel;
    }

    private static String addStartSlash(String str) {
        return !str.startsWith("/") ? "/" + str : str;
    }

    private static String addLastSlash(String str) {
        return !str.endsWith("/") ? str + "/" : str;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        if (this == other)
            return true;
        if (other instanceof ODUrl otherURL) {
            boolean schemeMatch = this.scheme.equals(otherURL.getScheme());
            boolean authorityMatch = this.authority.equals(otherURL.getAuthority());
            boolean pathMatch = this.path.equals(otherURL.getPath());
            boolean queryMatch = this.query.equals(otherURL.getQuery());
            boolean fragmentMatch = this.fragment.equals(otherURL.getFragment());
            return schemeMatch && authorityMatch && pathMatch && queryMatch && fragmentMatch;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.scheme, this.authority, this.path, this.query, this.fragment);
    }

    @Override
    public String toString() {
        StringBuilder urlBuf = new StringBuilder(77);
        if (!this.scheme.isEmpty()) {
            urlBuf.append(this.scheme).append("://");
        }
        if (!this.authority.isEmpty()) {
            urlBuf.append(this.authority);
        }
        if (!this.path.isEmpty()) {
            urlBuf.append(this.path);
        }
        if (!this.query.isEmpty()) {
            urlBuf.append("?").append(this.query);
        }
        if (!this.fragment.isEmpty()) {
            urlBuf.append("#").append(this.fragment);
        }
        return urlBuf.toString();
    }
}