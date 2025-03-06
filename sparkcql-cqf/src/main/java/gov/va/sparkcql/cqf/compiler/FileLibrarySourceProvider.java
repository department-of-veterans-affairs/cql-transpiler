package gov.va.sparkcql.cqf.compiler;

import org.cqframework.cql.cql2elm.LibrarySourceProvider;
import org.hl7.elm.r1.VersionedIdentifier;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileLibrarySourceProvider implements LibrarySourceProvider {

    private String path;
    protected List<String> sources;

    public FileLibrarySourceProvider(String path) {
        this.path = path;
        if (path == null || path.isEmpty()) {
            sources = List.of();
        } else {
            this.sources = findFiles(path)
                    .map(this::readFile)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public InputStream getLibrarySource(VersionedIdentifier libraryIdentifier) {
        validate();
        return sources.stream()
                .filter(source -> CqlParser.parseVersionedIdentifier(source).equals(libraryIdentifier))
                .map(source -> new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8)))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Unable to find " + libraryIdentifier.toString() + " in repository."));
    }

    private void validate() {
        if (this.sources.isEmpty()) {
            throw new RuntimeException("Attempted to read from an empty repository. Check location '" + path + "'.");
        }
    }

    private Stream<String> findFiles(String rootPath) {
        var fileList = listFilesRecursively(new File(rootPath)).filter(f -> !f.isDirectory());
        var extFilter = fileList;
        extFilter = fileList.filter(f -> f.getName().endsWith("cql"));
        return extFilter.map(File::getAbsolutePath);
    }

    private Stream<File> listFilesRecursively(File rootPath) {
        var rootFiles = rootPath.listFiles();
        if (rootFiles == null) {
            return Stream.of();
        }
        var fileAndFolderList = List.of(rootFiles);
        var descendants = fileAndFolderList.stream().flatMap(this::listFilesRecursively).collect(Collectors.toList());
        return Stream.concat(fileAndFolderList.stream(), descendants.stream());
    }

    private String readFile(String path) {
        try {
            return java.nio.file.Files.readString(Path.of(path));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
