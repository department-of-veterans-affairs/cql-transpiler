package gov.va.sparkcql.pipeline.preprocessor;

import gov.va.sparkcql.pipeline.Component;

public interface Preprocessor extends Component {

    public void apply();
}