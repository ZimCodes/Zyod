package xyz.zimtools.zyod;

import java.io.InputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Handles file operations.
 */
public final class Writer {
    public static String readMimeFile() {
        try (InputStream input = AppConfig.getMimeStream()) {
            if (input == null) {
                throw new FileNotFoundException(AppConfig.MIME_FILE + " resource cannot " +
                        "be found!");
            }
            try (InputStreamReader reader = new InputStreamReader(input);
                 BufferedReader bufferedReader = new BufferedReader(reader)) {
                Optional<String> mimeTypes = bufferedReader.lines()
                        .map(line -> line.replaceAll(", +", ",")
                                .replace("[", "")
                                .replace("]", "")
                                .replace("\"", "")
                                .replace("\\", "\"")
                                .trim())
                        .reduce((acc, nextLine) -> acc + nextLine);
                return mimeTypes.orElse("");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    public static List<String> readInputFile(File inputFile) {
        List<String> urls = new ArrayList<>();
        try (Scanner scanner = new Scanner(inputFile)) {
            while (scanner.hasNext()) {
                String url = scanner.nextLine();
                urls.add(url);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return urls;
    }
}