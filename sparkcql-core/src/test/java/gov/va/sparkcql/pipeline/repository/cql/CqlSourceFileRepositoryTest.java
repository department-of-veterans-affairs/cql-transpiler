package gov.va.sparkcql.pipeline.repository.cql;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.hl7.elm.r1.VersionedIdentifier;
import org.junit.jupiter.api.Test;

import gov.va.sparkcql.configuration.EnvironmentConfiguration;
import gov.va.sparkcql.pipeline.repository.cql.CqlSourceFileRepository;

public class CqlSourceFileRepositoryTest {

    @Test
    public void should_support_basic_read_ops() {
        var cfg = new EnvironmentConfiguration();
        cfg.writeSetting(
                CqlSourceFileRepository.CQL_SOURCE_FILE_REPOSITORY_PATH,
                "./src/test/resources/mock-model/cql");
        cfg.writeSetting(
                CqlSourceFileRepository.CQL_SOURCE_FILE_REPOSITORY_EXT,
                "cql");

        var repository = new CqlSourceFileRepository(cfg);
        assertNotNull(repository.readById(new VersionedIdentifier().withId("MOCK_LIBRARY").withVersion("1.0")));
        assertNull(repository.readById(new VersionedIdentifier().withId("MOCK_LIBRARY")));
    }

    @Test
    public void should_gracefully_ignore_when_unconfigured() {
        var cfg = new EnvironmentConfiguration();
        cfg.writeSetting(
                CqlSourceFileRepository.CQL_SOURCE_FILE_REPOSITORY_PATH,
                "./src/test/resources/mock/cql");
        cfg.writeSetting(
                CqlSourceFileRepository.CQL_SOURCE_FILE_REPOSITORY_EXT,
                "cql");

        var repository = new CqlSourceFileRepository(cfg);
        assertNotNull(repository);
        assertFalse(repository.exists(new VersionedIdentifier()));
    }
}