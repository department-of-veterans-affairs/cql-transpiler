package gov.va.sparkcql.pipeline.preprocessor;

import gov.va.sparkcql.pipeline.Component;
import gov.va.sparkcql.pipeline.Pipeline;

public interface Preprocessor extends Component {

    public void apply(Pipeline pipeline);
}