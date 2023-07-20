package gov.va.sparkcql.adapter.library;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.hl7.elm.r1.VersionedIdentifier;
import org.junit.jupiter.api.Test;

import gov.va.sparkcql.Configuration;
import gov.va.sparkcql.TestBase;

public class FileLibraryAdapterTest extends TestBase {
  
    @Test
    public void should_support_basic_read_ops() {
        var adapter = new FileLibraryAdapter("./src/test/resources/cql");
        assertTrue(adapter.getLibrarySource(new VersionedIdentifier().withId("BasicRetrieve").withVersion("1.0")).length() > 0);
        assertTrue(adapter.containsLibrary(new VersionedIdentifier().withId("BasicRetrieve").withVersion("1.0")));
        assertFalse(adapter.containsLibrary(new VersionedIdentifier().withId("BasicRetrieve")));
    }

    @Test
    public void should_be_factory_creatable() {
        var factory = new FileLibraryAdapterFactory();
        var cfg = new Configuration();
        cfg.write("sparkcql.filelibraryadapter.path", "./src/test/resources/cql");
        var adapter = factory.create(cfg, this.getSpark());
        assertNotNull(adapter);
        assertTrue(adapter.getLibrarySource(new VersionedIdentifier().withId("ComplexLiteral").withVersion("2.1")).length() > 0);
    }

    @Test
    public void should_gracefully_ignore_when_unconfigured() {
        var factory = new FileLibraryAdapterFactory();
        var cfg = new Configuration();
        var adapter = factory.create(cfg, this.getSpark());
        assertNotNull(adapter);
        assertFalse(adapter.containsLibrary(new VersionedIdentifier()));
    }
}