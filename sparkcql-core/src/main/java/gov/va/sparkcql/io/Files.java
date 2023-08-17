package gov.va.sparkcql.io;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public final class Files {

    private Files() {}
    
    public static void writeText(String text, String path) {
        try (var out = new PrintWriter(path)) {
            out.println(text);
            out.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
