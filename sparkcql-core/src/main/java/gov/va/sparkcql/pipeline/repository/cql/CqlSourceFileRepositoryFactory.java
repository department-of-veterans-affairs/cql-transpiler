package gov.va.sparkcql.pipeline.repository.cql;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.runtime.SparkFactory;

public class CqlSourceFileRepositoryFactory extends CqlSourceRepositoryFactory {

    public static final String CQL_SOURCE_FILE_REPOSITORY_PATH = "sparkcql.cqlsourcefilerepository.path";

    public CqlSourceFileRepositoryFactory(Configuration configuration) {
        super(configuration);
    }

    @Override
    public CqlSourceRepository create(SparkFactory sparkFactory) {
        return new CqlSourceFileRepository(
                getConfiguration().readSetting(CQL_SOURCE_FILE_REPOSITORY_PATH, "./"));
    }
}