package gov.va.sparkcql.repository.cql;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.hl7.elm.r1.VersionedIdentifier;
import org.junit.jupiter.api.Test;

import gov.va.sparkcql.configuration.SystemConfiguration;

public class CqlSourceFileRepositoryTest {

    SystemConfiguration cfg;
  
    public CqlSourceFileRepositoryTest() {
        this.cfg = new SystemConfiguration();
        this.cfg.write("", "./src/test/resources/sample");
        this.cfg.write("", "cql");
    }

    @Test
    public void should_support_basic_read_ops() {
        var repository = new CqlSourceFileRepository(cfg);
        assertNotNull(repository.findOne(new VersionedIdentifier().withId("SAMPLE_LIBRARY").withVersion("1.0")));
        assertNull(repository.findOne(new VersionedIdentifier().withId("SAMPLE_LIBRARY")));
    }

    @Test
    public void should_gracefully_ignore_when_unconfigured() {
        var repository = new CqlSourceFileRepository(cfg);
        assertNotNull(repository);
        assertFalse(repository.exists(new VersionedIdentifier()));
    }
}