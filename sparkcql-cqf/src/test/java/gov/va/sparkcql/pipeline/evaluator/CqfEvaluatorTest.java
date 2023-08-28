package gov.va.sparkcql.pipeline.evaluator;

import gov.va.sparkcql.domain.LibraryCollection;
import gov.va.sparkcql.io.Resources;
import gov.va.sparkcql.pipeline.model.ModelAdapterResolver;
import org.cqframework.cql.elm.serializing.jackson.ElmJsonLibraryReader;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;

public class CqfEvaluatorTest {

    @Test
    public void should_evaluate_mock_fhir_measure() throws IOException {
        var libraryContents = Resources.read("elm/mock.json");
        var reader = new ElmJsonLibraryReader();
        var libraryCollection = new LibraryCollection();
        libraryCollection.add(reader.read(libraryContents));


        var evaluator = new CqfEvaluatorFactory().create(
                libraryCollection,
                new ModelAdapterResolver(Set.of()),
                null);

        evaluator.evaluate("12345", null);
    }

    @Test
    public void should_evaluate_mock_qdm_measure() {
    }
}
