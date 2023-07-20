package gov.va.sparkcql.adapter.library;

import org.apache.spark.sql.SparkSession;

import gov.va.sparkcql.Configuration;

public class FileLibraryAdapterFactory implements LibraryAdapterFactory {

    @Override
    public LibraryAdapter create(Configuration configuration, SparkSession spark) {
        return new FileLibraryAdapter(configuration.read("sparkcql.filelibraryadapter.path", null));
    }
}