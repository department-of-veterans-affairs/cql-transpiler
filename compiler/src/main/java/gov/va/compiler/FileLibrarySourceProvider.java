package gov.va.compiler;

import org.cqframework.cql.cql2elm.LibrarySourceProvider;
import org.hl7.elm.r1.VersionedIdentifier;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileLibrarySourceProvider implements LibrarySourceProvider {

    private Map<VersionedIdentifier, String> cqlLibraries;

    public FileLibrarySourceProvider(String path) {
        cqlLibraries = listFilesRecursively(new File(path))
            .filter(f -> !f.isDirectory())
            .filter(f -> f.getName().endsWith("cql"))
            .map(file -> readFile(file))
            .collect(Collectors.toMap(fileAsString -> CqlParser.parseVersionedIdentifier(fileAsString), fileAsString -> fileAsString));
    }

    /**
     * @param rootPath Root path to look for files in
     * @return Stream of files
     */
    private Stream<File> listFilesRecursively(File rootPath) {
        var rootFiles = rootPath.listFiles();
        if (rootFiles == null) {
            return Stream.of();
        }
        var fileAndFolderList = List.of(rootFiles);
        var descendants = fileAndFolderList.stream().flatMap(this::listFilesRecursively).collect(Collectors.toList());
        return Stream.concat(fileAndFolderList.stream(), descendants.stream());
    }

    private String readFile(File file) {
        try {
            return java.nio.file.Files.readString(file.toPath());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public InputStream getLibrarySource(VersionedIdentifier libraryIdentifier) {
        var libraryContents = cqlLibraries.get(libraryIdentifier);
        return libraryContents == null ? null : new ByteArrayInputStream(libraryContents.getBytes());
    }
}
