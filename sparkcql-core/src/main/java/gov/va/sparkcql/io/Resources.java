package gov.va.sparkcql.io;

import gov.va.sparkcql.log.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Resources {

    public static String read(String path) {
        var classLoader = Resources.class.getClassLoader();
        var resourceStream = classLoader.getResource(path);
        try (var data = resourceStream.openStream()) {
            return new String(data.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception ex) {
            throw new RuntimeException("No resources found at '" + path + "'.");
        }
    }

    public static Stream<String> readAll(String path) {
        try {
            var filePaths = getResourceFiles(path);
            return filePaths.stream().map(Resources::read);
        } catch (Exception e) {
            throw new RuntimeException("Unable to read resources at location '" + path + "'.", e);
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