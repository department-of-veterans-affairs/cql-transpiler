package gov.va.sparkcql.adapter.library;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.hl7.elm.r1.VersionedIdentifier;
import org.junit.jupiter.api.Test;

public class FileLibraryAdapterTest {
  
    FileLibraryAdapter adapter = new FileLibraryAdapter("./src/test/resources/cql");

    @Test
    public void should_load_basic_by_fqn() {
        assertTrue(adapter.getLibrarySource(new VersionedIdentifier().withId("BasicRetrieve").withVersion("1.0")).length() > 0);
    }
}