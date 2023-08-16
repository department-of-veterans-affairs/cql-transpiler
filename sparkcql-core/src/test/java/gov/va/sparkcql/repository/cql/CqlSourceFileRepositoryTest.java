package gov.va.sparkcql.repository.cql;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.hl7.elm.r1.VersionedIdentifier;
import org.junit.jupiter.api.Test;

import gov.va.sparkcql.repository.FileRepositoryConfiguration;
import gov.va.sparkcql.repository.MutableFileRepositoryConfiguration;
import gov.va.sparkcql.repository.cql.CqlSourceFileRepository;

public class CqlSourceFileRepositoryTest {

    FileRepositoryConfiguration cfg = new MutableFileRepositoryConfiguration("./src/test/resources/sample", "cql");
  
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