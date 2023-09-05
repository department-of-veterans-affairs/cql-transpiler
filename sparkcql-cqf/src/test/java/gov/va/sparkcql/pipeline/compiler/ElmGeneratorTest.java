package gov.va.sparkcql.pipeline.compiler;

import gov.va.sparkcql.domain.Plan;
import gov.va.sparkcql.io.ElmWriter;
import gov.va.sparkcql.pipeline.repository.cql.CqlSourceFileRepository;
import gov.va.sparkcql.types.QualifiedIdentifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ElmGeneratorTest {

    protected Compiler compiler;

    @BeforeEach
    public void setup() {
        compiler = new CqfCompiler(
                new CqlSourceFileRepository("./src/test/resources/cql"));
    }

//    @Test
//    public void should_generate_elms() {
//        // Utility method to create ELMs for testing elsewhere
//        // TODO: Replace this with something less hacky.
//        var output = compiler.compile(List.of(new QualifiedIdentifier().withId("DeduplicateRetrieves")));
//        ElmWriter.write(output.getLibraries());
//    }
}