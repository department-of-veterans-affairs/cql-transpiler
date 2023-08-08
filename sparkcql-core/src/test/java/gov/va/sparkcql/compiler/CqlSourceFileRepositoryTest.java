package gov.va.sparkcql.compiler;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.hl7.elm.r1.VersionedIdentifier;
import org.junit.jupiter.api.Test;

import gov.va.sparkcql.common.configuration.ConfigKey;
import gov.va.sparkcql.common.configuration.Configuration;
import gov.va.sparkcql.compiler.CqlSourceFileRepository;

public class CqlSourceFileRepositoryTest {
  
    @Test
    public void should_support_basic_read_ops() {
        var repository = new CqlSourceFileRepository("./src/test/resources/cql");
        var x = repository.findOne(new VersionedIdentifier().withId("BasicRetrieve").withVersion("1.0"));
        assertNotNull(repository.findOne(new VersionedIdentifier().withId("BasicRetrieve").withVersion("1.0")));
        assertNotNull(repository.findOne(new VersionedIdentifier().withId("BasicRetrieve").withVersion("1.0")));
        assertNull(repository.findOne(new VersionedIdentifier().withId("BasicRetrieve")));
    }

    @Test
    public void should_be_default_constructable() {
        var cfg = new Configuration();
        cfg.write(ConfigKey.SPARKCQL_CQLSOURCEREPOSITORY_PATH, "./src/test/resources/cql");
        var repository = new CqlSourceFileRepository(cfg);
        assertNotNull(repository);
        assertNotNull(repository.findOne(new VersionedIdentifier().withId("ComplexLiteral").withVersion("2.1")));
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