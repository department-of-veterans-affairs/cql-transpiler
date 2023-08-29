package gov.va.sparkcql.fixture.fhir;

import gov.va.sparkcql.configuration.EnvironmentConfiguration;
import gov.va.sparkcql.pipeline.repository.cql.CqlSourceFileRepository;

public class FhirConfiguration extends EnvironmentConfiguration {
    public FhirConfiguration() {
        this.writeSetting(
                CqlSourceFileRepository.CQL_SOURCE_FILE_REPOSITORY_PATH,
                "./src/test/resources/cql");
    }
}