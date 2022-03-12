package xyz.zimtools.zyod;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class WriterTest {
    /**
     * Test the retrieval of mime types from a JSON file.
     * */
    @Test
    @DisplayName("Get Mime Types")
    void readMimeFile() {
        assertNotEquals(Writer.readMimeFile(), "");
    }
}