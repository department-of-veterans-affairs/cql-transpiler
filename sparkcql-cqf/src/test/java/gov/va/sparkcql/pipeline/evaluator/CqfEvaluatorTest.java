package gov.va.sparkcql.pipeline.evaluator;

import gov.va.sparkcql.configuration.EnvironmentConfiguration;
import gov.va.sparkcql.domain.Plan;
import gov.va.sparkcql.io.Resources;
import gov.va.sparkcql.pipeline.model.ModelAdapterSet;
import org.cqframework.cql.elm.serializing.jackson.ElmJsonLibraryReader;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class CqfEvaluatorTest {

    @Test
    public void should_evaluate_mock_literal_measure() throws IOException {
        var libraryContents = Resources.read("elm/complex-literal.json");
        var reader = new ElmJsonLibraryReader();
        var plan = new Plan().withLibrary(reader.read(libraryContents));

        var evaluator = new CqfEvaluatorFactory(new EnvironmentConfiguration()).create(
                plan,
                new ModelAdapterSet(List.of()),
                null);

        evaluator.evaluate("12345", null);
    }

    @Test
    public void should_evaluate_mock_fhir_measure() {
    }

    @Test
    public void should_evaluate_mock_qdm_measure() {
    }
}