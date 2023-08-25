package gov.va.sparkcql.pipeline.preprocessor;

import ca.uhn.fhir.context.FhirContext;
import gov.va.sparkcql.io.Resources;
import org.hl7.fhir.r4.model.Bundle;

public class BundleDataLoaderPreprocessor implements Preprocessor {

    @Override
    public void apply() {
        // Stream the bundles stored as resources files.
        var contents = Resources.readAll("bundles");

        // For each one, load it into a HAPI bundle structure.
        var ctx = FhirContext.forR4();
        var parser = ctx.newJsonParser();
        var bundles = contents.map(content ->
                parser.parseResource(Bundle.class, content));

        // For each one,
    }
}