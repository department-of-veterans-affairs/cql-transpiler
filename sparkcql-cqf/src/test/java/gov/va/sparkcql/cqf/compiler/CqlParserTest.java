package gov.va.sparkcql.cqf.compiler;

import org.hl7.elm.r1.VersionedIdentifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CqlParserTest {

    @Test
    public void should_parse_cql_identifiers_with_version() {
        var id = CqlParser.parseVersionedIdentifier("library MyLibrary version '1'\r\ninclude MyDependency version '2'");
        assertEquals(new VersionedIdentifier().withId("MyLibrary").withVersion("1"), id);
    }

    @Test
    public void should_parse_cql_identifiers_without_version() {
        var id = CqlParser.parseVersionedIdentifier("library Versionless_Library\r\ninclude MyDependency version '2'");
        assertEquals(new VersionedIdentifier().withId("Versionless_Library"), id);
    }
}