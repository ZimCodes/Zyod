package xyz.zimtools.zyod.assets;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import xyz.zimtools.zyod.fixtures.URLDefault;
import xyz.zimtools.zyod.fixtures.asserts.URLAssert;

import static org.junit.jupiter.api.Assertions.assertEquals;

class URLPathTest {
    /**
     * Test popping paths of urls without a path.
     */
    @ParameterizedTest
    @ValueSource(strings = {"https://example.com", "https://example.com/", "https://www.example.com"})
    void noPath(String url) {
        ODUrl parsedURL = new ODUrl(url);
        URLAssert.popAssert("", parsedURL);
        assertEquals("/", parsedURL.getPath(), "No path should be present in " + url);
    }

    @Test
    void pathPop() {
        String[] paths = {"breakfast", "common", "folder"};
        ODUrl parsedURL = new ODUrl(URLDefault.BASE_URL + "/" + String.join("/", paths));

        URLAssert.popAssert(paths[2], parsedURL);
        URLAssert.urlAssert(String.format("%s/%s/%s", URLDefault.BASE_URL, paths[0], paths[1]),
                parsedURL.toString());

        URLAssert.popAssert(paths[1], parsedURL);
        URLAssert.urlAssert(String.format("%s/%s", URLDefault.BASE_URL, paths[0]), parsedURL.toString());

        URLAssert.popAssert(paths[0], parsedURL);
        URLAssert.urlAssert(URLDefault.BASE_URL, parsedURL.toString());
    }

    @Test
    void addPath() {
        String url = "https://example.com";
        ODUrl parsedUrl = new ODUrl(url);

        String rel = "/doc_folder";
        parsedUrl.addPath("doc_folder");
        URLAssert.urlAssert(url + rel, parsedUrl.toString());

        rel += "/common";
        parsedUrl.addPath("/common");
        URLAssert.urlAssert(url + rel, parsedUrl.toString());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "https://example.com",
            "https://api.example.com",
            "https://example.org/index.mkv/",
            "https://example.net/common/memes/index.html.aspx/",
            "https://example.com/golden Eyes/#piano/cat-45-3.txt",
    })
    void isNotURLFile(String url) {
        URLAssert.fileFalse(url);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "https://api.example.com/index.html",
            "https://example.com/ax/common/index.html",
            "https://example.com/cloud-storage/index.mp4.ogg",
            "example.net/index.html",
            "https://www.example.net/index-8.flv5",
            "https://www.example.com/az/index.html?dir=/LessonsPiano.cpp/mystery 0 Folder.txt",
            "https://www.example.com/az/index.html?dir=/LessonsPiano.cpp",
            "https://www.example.com/az/index.html#/cat.txt",
    })
    void isURLFile(String url){
        URLAssert.fileTrue(url);
    }
}