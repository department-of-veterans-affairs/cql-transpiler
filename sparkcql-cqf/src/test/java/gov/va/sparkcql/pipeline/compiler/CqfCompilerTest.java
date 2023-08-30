package gov.va.sparkcql.pipeline.compiler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import gov.va.sparkcql.domain.Plan;
import gov.va.sparkcql.io.ElmWriter;
import gov.va.sparkcql.pipeline.compiler.CqfCompiler;
import gov.va.sparkcql.pipeline.repository.cql.CqlSourceFileRepositoryFactory;
import gov.va.sparkcql.types.QualifiedIdentifier;
import org.hl7.cql_annotations.r1.CqlToElmError;
import org.hl7.elm.r1.Library;
import org.hl7.elm.r1.VersionedIdentifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gov.va.sparkcql.configuration.EnvironmentConfiguration;
import gov.va.sparkcql.pipeline.compiler.Compiler;
import gov.va.sparkcql.pipeline.repository.cql.CqlSourceFileRepository;

public class CqfCompilerTest {

    protected Compiler compiler;

    @BeforeEach
    public void setup() {
        compiler = new CqfCompiler(
                new CqlSourceFileRepository("./src/test/resources/cql"));
    }

    @Test
    public void should_compile_a_literal_cql() {
        assertEquals(
                "MyLibrary",
                compiler
                        .compile("library MyLibrary version '1'")
                        .getLibrary(0).orElseThrow().getIdentifier().getId());
    }

    @Test
    public void should_support_multiple_independent_modules() {
        var output = compiler.compile(
                "library MyLibrary version '1'",
                "library MyOtherLibrary version '2'",
                "library OurLibrary version '3'"
        );
        assertLibraries(3, output);
    }

    @Test
    public void should_support_multiple_dependent_modules() {
        var output = compiler.compile(
                "library MyLibrary version '1'\ninclude MyDependency version '2'",
                "library MyDependency version '2'"
        );
        assertLibraries(2, output);
    }

    @Test
    public void should_support_anonymous_modules() {
        var output = compiler.compile(
                "define myconst: 123", "define myconst: 456", "define myconst: 789"
        );
        assertLibraries(3, output);
    }

    @Test
    public void should_handle_duplicates_with_differing_versions() {
        var output = compiler.compile(
                "library MyLibrary version '1'", "library MyLibrary version '2'"
        );
        assertLibraries(2, output);
    }

    @Test
    public void should_allow_file_repository_loading() {
        // Compiling by ID will force use of the CqlSourceFileRepository.
        var output = compiler.compile(List.of(new QualifiedIdentifier().withId("ComplexLiteral").withVersion("2.1")));
        var json = output.getLibraries().get(0);
        assertLibraries(1, output);
    }

    private void assertLibraries(int expectedCount, Plan plan) {
        assertEquals(expectedCount, plan.getLibraries().size());
        ElmWriter.write(plan.getLibraries());
        plan.getLibraries().forEach(l -> {
            var checker = new CqlErrorChecker(l);
            if (checker.hasErrors()) {
                throw new RuntimeException(checker.toPrettyString());
            }
        });
    }
}