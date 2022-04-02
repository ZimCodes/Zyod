package xyz.zimtools.zyod;

import java.io.*;
import java.util.*;

/**
 * Handles file operations.
 */
public final class Writer {

    /**
     * Extract data from Mime file.
     * <p>
     *     The extracted mime data will be transformed into a comma delimited format.
     * </p>
     *
     * @return all mime types in a comma separated format.*/
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

    /**
     * Read URLs from a file.
     * <p>
     *     Retrieve links from a file. Each line in the file must contain one link to an OD to
     *     operate on.
     * </p>
     * @param inputFile the input file
     * @return list of OD references
     * */
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