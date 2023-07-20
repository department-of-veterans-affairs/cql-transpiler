package gov.va.sparkcql.common;

import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.cqframework.cql.elm.serializing.ElmLibraryWriterFactory;
import org.hl7.elm.r1.Library;

final public class LibraryWriter {

    private LibraryWriter() {
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
}
