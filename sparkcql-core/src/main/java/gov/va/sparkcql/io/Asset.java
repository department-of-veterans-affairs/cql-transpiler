package gov.va.sparkcql.io;

import gov.va.sparkcql.runtime.DefaultSparkFactory;
import org.apache.spark.sql.SparkSession;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class Asset implements Serializable {

    private final String MARKER = "://";
    private final AssetModality assetModality;
    private String rawPath = "";

    public Asset(String qualifiedPath) {
        if (!qualifiedPath.contains(MARKER)) {
            throw new RuntimeException("Attempted to de-qualify an already de-qualified asset path.");
        }

        var positionStart = qualifiedPath.indexOf(MARKER);
        var positionEnd = qualifiedPath.indexOf(MARKER) + MARKER.length();
        this.assetModality = AssetModality.valueOf(qualifiedPath.substring(0, positionStart).toUpperCase());
        this.rawPath = qualifiedPath.substring(positionEnd);
    }

    public Asset(AssetModality assetModality, String rawPath) {
        if (rawPath.contains(MARKER)) {
            throw new RuntimeException("Attempted to qualify an already qualified asset path.");
        }

        this.assetModality = assetModality;
        this.rawPath = assetModality.name().toLowerCase() + rawPath;
    }

    public static Asset of(String qualifiedPath) {
        return new Asset(qualifiedPath);
    }

    public List<String> read() {
        return read(null);
    }

    public List<String> read(SparkSession spark) {
        switch (this.assetModality) {
            case FILE:
                return Files.readFiles(rawPath, "*");

            case CLASSPATH:
                return Resources.readAll(rawPath).collect(Collectors.toList());

            case SPARK:
                if (spark == null)
                    throw new RuntimeException("Attempted to read Spark resource without specifying a SparkSession.");

                var rows = spark.read().text(rawPath);
                return rows.collectAsList().stream()
                        .map(r -> r.getString(0)).collect(Collectors.toList());
        }

        return List.of();
    }

    public enum AssetModality {
        CLASSPATH,
        FILE,
        SPARK;
    }
}