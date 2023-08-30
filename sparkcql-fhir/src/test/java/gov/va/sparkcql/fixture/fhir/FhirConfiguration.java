package gov.va.sparkcql.fixture.fhir;

import gov.va.sparkcql.configuration.EnvironmentConfiguration;
import gov.va.sparkcql.pipeline.repository.cql.CqlSourceFileRepository;
import gov.va.sparkcql.pipeline.repository.cql.CqlSourceFileRepositoryFactory;

public class FhirConfiguration extends EnvironmentConfiguration {
    public FhirConfiguration() {
        this.writeSetting(
                CqlSourceFileRepositoryFactory.CQL_SOURCE_FILE_REPOSITORY_PATH,
                "./src/test/resources/cql");
    }
}