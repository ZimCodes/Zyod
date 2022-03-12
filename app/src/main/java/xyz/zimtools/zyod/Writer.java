package xyz.zimtools.zyod;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.InputStream;

import java.util.Optional;
/**
 * Handles all file operations.
 * */
public final class Writer {
    public static String readMimeFile() {
        String resource = "mimes.json";
        try (InputStream input = Writer.class.getClassLoader().getResourceAsStream(resource)) {
            if (input == null) {
                throw new FileNotFoundException(resource + " resource cannot be found!");
            }
            try (InputStreamReader reader = new InputStreamReader(input);
                 BufferedReader bufferedReader = new BufferedReader(reader)) {
                Optional<String> mimeTypes = bufferedReader.lines()
                        .map(line -> line.replaceAll(", +", ",")
                                .replace("[", "")
                                .replace("]", "")
                                .replace("\"","")
                                .replace("\\","\"")
                                .trim())
                        .reduce((acc, nextLine) -> acc + nextLine);
                return mimeTypes.orElse("");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }
}