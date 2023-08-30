package gov.va.sparkcql.io;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public final class Files {

    private Files() {}

    public static String readFile(String path) {
        try {
            return java.nio.file.Files.readString(Path.of(path));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> readFiles(String path, String ext) {
        return Directory.find(path, ext)
                .map(Files::readFile)
                .collect(Collectors.toList());
    }

    public static void writeText(String text, String path) {
        try (var out = new PrintWriter(path)) {
            out.println(text);
            out.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
