package gov.va.sparkcql.pipeline.evaluator;

import gov.va.sparkcql.domain.Plan;
import gov.va.sparkcql.io.Resources;
import gov.va.sparkcql.pipeline.model.ModelAdapterResolver;
import org.cqframework.cql.elm.serializing.jackson.ElmJsonLibraryReader;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;

public class CqfEvaluatorTest {

    @Test
    public void should_evaluate_mock_literal_measure() throws IOException {
        var libraryContents = Resources.read("elm/complex-literal.json");
        var reader = new ElmJsonLibraryReader();
        var plan = new Plan().withLibrary(reader.read(libraryContents));

        var evaluator = new CqfEvaluatorFactory().create(
                plan,
                new ModelAdapterResolver(Set.of()),
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
