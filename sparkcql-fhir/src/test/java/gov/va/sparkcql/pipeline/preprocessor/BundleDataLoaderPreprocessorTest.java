package gov.va.sparkcql.pipeline.preprocessor;

import org.junit.jupiter.api.Test;

public class BundleDataLoaderPreprocessorTest {

    @Test
    public void should_load_bundles_from_resources() {
        var loader = new BundleDataLoaderPreprocessor();
        loader.apply();
    }
}