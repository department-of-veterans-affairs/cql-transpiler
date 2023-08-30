package gov.va.sparkcql.pipeline.preprocessor;

import gov.va.sparkcql.configuration.Component;

public interface Preprocessor extends Component {

    public void apply();
}