package gov.va.sparkcql.pipeline.repository.cql;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.pipeline.runtime.SparkFactory;

public class CqlSourceFileRepositoryFactory implements CqlSourceRepositoryFactory {

    public static final String CQL_SOURCE_FILE_REPOSITORY_PATH = "sparkcql.cqlsourcefilerepository.path";

    @Override
    public CqlSourceRepository create(Configuration configuration, SparkFactory sparkFactory) {
        return new CqlSourceFileRepository(
                configuration.readSetting(CQL_SOURCE_FILE_REPOSITORY_PATH, "./"));
    }
}