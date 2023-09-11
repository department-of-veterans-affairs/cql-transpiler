package gov.va.sparkcql.cqf.compiler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.cqframework.cql.elm.serializing.ElmLibraryWriterFactory;
import org.hl7.elm.r1.Library;
import org.hl7.elm.r1.VersionedIdentifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CqfCompilerTest {

    private final String ELM_OUTPUT_FOLDER = "./.temp/";
    protected CqfCompiler compiler;

    @BeforeEach
    public void setup() {
        compiler = new CqfCompiler(
                new FileLibrarySourceProvider("./src/test/resources/cql"));
    }

    @Test
    public void should_compile_a_literal_cql() {
        assertEquals(
                "MyLibrary",
                compiler.compile("library MyLibrary version '1'")
                        .get(0).getIdentifier().getId());
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
    public void should_generate_elms() {
        // Utility method to create ELMs for testing elsewhere
        var output = compiler.compile(List.of(new VersionedIdentifier().withId("ComplexLiteral").withVersion("2.1")));
        assertLibraries(1, output);
    }

    @Test
    public void should_allow_file_repository_loading() {
        // Compiling by ID will force use of the CqlSourceFileRepository.
        var output = compiler.compile(List.of(new VersionedIdentifier().withId("ComplexLiteral").withVersion("2.1")));
        assertLibraries(1, output);
    }

    @Test
    public void should_combine_identified_and_anonymous_modules() {
        var output = compiler.compile(
                List.of(new VersionedIdentifier().withId("ComplexLiteral").withVersion("2.1")),
                "define myconst: 123"
        );
        assertLibraries(2, output);
    }

    private void assertLibraries(int expectedCount, List<Library> libraries) {
        assertEquals(expectedCount, libraries.size());
        writeElm(libraries);
        libraries.forEach(l -> {
            var checker = new CqlErrorChecker(l);
            if (checker.hasErrors()) {
                throw new RuntimeException(checker.toPrettyString());
            }
        });
    }

    public void writeElm(List<Library> libraries) {
        libraries.forEach(library -> {
            var name = String.valueOf(library.toString().hashCode());
            if (library.getIdentifier().getId() != null) {
                name = library.getIdentifier().getId();
            }

            try {
                Files.createDirectories(Paths.get(ELM_OUTPUT_FOLDER));
                var writer = new FileWriter(new File(ELM_OUTPUT_FOLDER + name + ".json"));
                ElmLibraryWriterFactory.getWriter("application/elm+json").write(library, writer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}