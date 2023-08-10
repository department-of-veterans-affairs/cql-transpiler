package gov.va.sparkcql.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.hl7.elm.r1.VersionedIdentifier;
import org.junit.jupiter.api.Test;

import gov.va.sparkcql.common.configuration.ConfigKey;
import gov.va.sparkcql.common.configuration.Configuration;

public class CqlSourceFileRepositoryTest {
  
    @Test
    public void should_support_basic_read_ops() {
        var repository = new CqlSourceFileRepository("./src/test/resources/sample");
        assertNotNull(repository.findOne(new VersionedIdentifier().withId("SAMPLE_LIBRARY").withVersion("1.0")));
        assertNull(repository.findOne(new VersionedIdentifier().withId("SAMPLE_LIBRARY")));
    }

    @Test
    public void should_be_default_constructable() {
        var cfg = new Configuration();
        cfg.write(ConfigKey.SPARKCQL_CQLSOURCEREPOSITORY_PATH, "./src/test/resources/sample");
        var repository = new CqlSourceFileRepository(cfg);
        assertNotNull(repository);
        assertNotNull(repository.findOne(new VersionedIdentifier().withId("SAMPLE_LIBRARY").withVersion("1.0")));
    }

    @Test
    public void should_gracefully_ignore_when_unconfigured() {
        var cfg = new Configuration();
        cfg.write(ConfigKey.SPARKCQL_CQLSOURCEREPOSITORY_PATH, null);
        var repository = new CqlSourceFileRepository(cfg);
        assertNotNull(repository);
        assertFalse(repository.exists(new VersionedIdentifier()));
    }
}