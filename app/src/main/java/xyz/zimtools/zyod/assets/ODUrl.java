package xyz.zimtools.zyod.assets;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ODUrl {
    private static final Pattern FILE_URL_PAT = Pattern.compile("[^/=#.]+\\." +
            "(?:[a-zA-Z0-9]{3,7}|[a-zA-Z][a-zA-Z0-9]|[0-9][a-zA-Z])$");
    private static final Pattern SPACE_PAT = Pattern.compile(" ");
    private static final String SPACE_ENCODING = "%20";
    private String scheme;
    private String authority;
    private String path;
    private String query;
    private String fragment;

    public ODUrl(String link) {
        URI uri;
        try {
            uri = new URI(link);
            this.init(uri);
        } catch (URISyntaxException e) {
            link = link.replaceAll(" ", "%20");
            link = link.replaceAll("\\[","%5B");
            link = link.replaceAll("]","%5D");
            try {
                this.init(new URI(link));
            } catch (URISyntaxException a) {
                a.printStackTrace();
            }
        }
    }

    private void init(URI uri) {
        this.scheme = uri.getScheme();
        if (this.scheme == null) {
            this.scheme = "";
        }
        this.authority = uri.getRawAuthority();
        if (this.authority == null) {
            this.authority = "";
        }
        this.path = uri.getRawPath();
        this.path = this.path == null || this.path.isEmpty() ? "/" : addStartSlash(this.path);
        this.query = uri.getRawQuery();
        this.addQueryPathToPath();
        this.fragment = uri.getRawFragment();
        this.addRefPathToPath();
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
    public static ODUrl joiner(String url, String rel) {
        ODUrl parsedUrl = new ODUrl(url);
        ODUrl parsedRel = new ODUrl(rel);
        if (parsedUrl.equals(parsedRel)) {
            return parsedUrl;
        }

        if (!encodeEquals(parsedUrl.getPath(), parsedRel.getPath())) {
            if (encodeContains(parsedUrl.getPath(), parsedRel.getPath())) {
                parsedUrl.setPath(parsedRel.getPath());
            } else {
                parsedUrl.setPath(slashJoin(parsedUrl.getPath(), parsedRel.getPath()));
            }
        }

        if (!encodeEquals(parsedUrl.getQuery(), parsedRel.getQuery())) {
            if (encodeContains(parsedUrl.getQuery(), parsedRel.getQuery())) {
                parsedUrl.setQuery(parsedRel.getQuery());
            } else {
                parsedUrl.setQuery(parsedUrl.getQuery() + "&" + parsedRel.getQuery());
            }
        }

        return parsedUrl;
    }

    /**
     * Determines if a link references a file.
     *
     * @param link the link to decipher
     * @return true if link is a file; false otherwise
     */
    public static boolean isFile(String link) {
        Matcher fileMat;
        boolean isAFile;
        try {
            URL url = new URL(link);
            fileMat = FILE_URL_PAT.matcher(url.getFile());
            isAFile = fileMat.find();
        } catch (MalformedURLException e) {
            fileMat = FILE_URL_PAT.matcher(link);
            isAFile = fileMat.find();
        }
        return isAFile;
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
            boolean schemeMatch = encodeEquals(this.scheme, otherURL.getScheme());
            boolean authorityMatch = encodeEquals(this.authority, otherURL.getAuthority());
            boolean pathMatch = encodeEquals(this.path, otherURL.getPath());
            boolean queryMatch = encodeEquals(this.query, otherURL.getQuery());
            boolean fragmentMatch = encodeEquals(this.fragment, otherURL.getFragment());
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

    /**
     * Determine if two urls are equal with and without using URL encoding.
     *
     * @param url the absolute URL
     * @param rel the relative URL
     * @return true if two urls are equal; false otherwise
     */
    private static boolean encodeEquals(String url, String rel) {
        return url.equals(rel) || SPACE_PAT.matcher(url).replaceAll(SPACE_ENCODING)
                .equals(SPACE_PAT.matcher(rel).replaceAll(SPACE_ENCODING));
    }

    /**
     * Determine if relative URL contains the absolute URL with and without using URL encoding.
     *
     * @param url the absolute URL
     * @param rel the relative URL
     * @return true if relative URL contains the absolute URL; false otherwise
     */
    private static boolean encodeContains(String url, String rel) {
        return rel.contains(url) || SPACE_PAT.matcher(rel).replaceAll(SPACE_ENCODING)
                .contains(SPACE_PAT.matcher(url).replaceAll(SPACE_ENCODING));
    }
}