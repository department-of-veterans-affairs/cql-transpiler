package gov.va.sparkcql.pipeline.preprocessor;

import gov.va.sparkcql.pipeline.Stage;

public interface Preprocessor extends Stage {

    public void apply();
}