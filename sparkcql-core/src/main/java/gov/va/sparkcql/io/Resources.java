package gov.va.sparkcql.io;

import gov.va.sparkcql.log.Log;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Resources {

    public static String read(String filePath) {
        var classLoader = Resources.class.getClassLoader();
        var resourceStream = classLoader.getResource(filePath);
        try (var data = resourceStream.openStream()) {
            return new String(data.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception ex) {
            throw new RuntimeException("No resources found at '" + filePath + "'.");
        }
    }

    public static Stream<String> readAll(String folderPath) {
        try {
            var filePaths = getResourceFiles(folderPath);
            return filePaths.stream().map(Resources::read);
        } catch (Exception e) {
            throw new RuntimeException("Unable to read resources at location '" + folderPath + "'.", e);
        }
    }

    private static List<String> getResourceFiles(String path) throws IOException {
        List<String> filenames = new ArrayList<>();

        try (InputStream in = getResourceAsStream(path); BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            String resource;

            while ((resource = br.readLine()) != null) {
                filenames.add(Paths.get(path, resource).toString());
            }
        }

        return filenames;
    }

    private static InputStream getResourceAsStream(String resource) throws IOException {
        try (InputStream in = getContextClassLoader().getResourceAsStream(resource)) {
            if (in != null) {
                return in;
            } else {
                return Resources.class.getResourceAsStream(resource);
            }
        }
    }

    private static ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
}