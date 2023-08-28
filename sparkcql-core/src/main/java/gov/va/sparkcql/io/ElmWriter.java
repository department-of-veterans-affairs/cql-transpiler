package gov.va.sparkcql.io;

import org.cqframework.cql.elm.serializing.ElmLibraryWriterFactory;
import org.hl7.elm.r1.Library;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.List;

public final class ElmWriter {

    private ElmWriter() {
    }

    public static void write(List<Library> libraries, String targetPath) {
        libraries.forEach(library -> {
            var name = java.util.UUID.randomUUID().toString();
            if (library.getIdentifier().getId() != null) {
                name = library.getIdentifier().getId();
            }

            try {
                Files.createDirectories(Paths.get(targetPath));
                var writer = new FileWriter(new File(targetPath + name + ".json"));
                ElmLibraryWriterFactory.getWriter("application/elm+json").write(library, writer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void write(List<Library> libraries) {
        write(libraries, "./.temp/");
    }
}