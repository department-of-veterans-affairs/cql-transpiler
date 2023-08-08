package gov.va.sparkcql.compiler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.hl7.elm.r1.VersionedIdentifier;
import org.junit.jupiter.api.Test;

import gov.va.sparkcql.common.di.Components;
import gov.va.sparkcql.common.spark.SparkFactory;

public class CompilerTest {

    @Test
    public void should_parse_cql_identifiers_with_version() {
        var id = new CqlParser().parseVersionedIdentifier("library MyLibrary version '1'\r\ninclude MyDependency version '2'");
        assertEquals(new VersionedIdentifier().withId("MyLibrary").withVersion("1"), id);
    }

    @Test
    public void should_parse_cql_identifiers_without_version() {
        var id = new CqlParser().parseVersionedIdentifier("library Versionless_Library\r\ninclude MyDependency version '2'");
        assertEquals(new VersionedIdentifier().withId("Versionless_Library"), id);
    }

    @Test
    public void should_not_break_spark() {
        // Confirms the proper Spark ANTLR version is loaded instead of CQF's higher version.
        var spark = Components.createOne(SparkFactory.class).create();
        var ds = spark.sql("select 12345 foo");
    }
}