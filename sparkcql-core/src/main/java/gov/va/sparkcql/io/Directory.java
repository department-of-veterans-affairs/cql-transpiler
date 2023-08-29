package gov.va.sparkcql.io;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.nio.file.Files;
import java.nio.file.Path;

public final class Directory {

    public static String currentDir() {
        return System.getProperty("user.dir");
    }

    public static Stream<String> find(String rootPath, String ext) {
        var fileList = listFilesRecursively(new File(rootPath)).filter(f -> !f.isDirectory());
        var extFilter = fileList;
        if (ext != "*") {
            extFilter = fileList.filter(f -> f.getName().endsWith(ext));
        }
        return extFilter.map(f -> f.getAbsolutePath());
    }

    public static Stream<File> listFilesRecursively(File rootPath) {
        var rootFiles = rootPath.listFiles();
        if (rootFiles == null) {
            return Stream.of();
        }
        var fileAndFolderList = List.of(rootFiles);
        var descendants = fileAndFolderList.stream().flatMap(f -> listFilesRecursively(f)).collect(Collectors.toList());
        return Stream.concat(fileAndFolderList.stream(), descendants.stream());
    }
}