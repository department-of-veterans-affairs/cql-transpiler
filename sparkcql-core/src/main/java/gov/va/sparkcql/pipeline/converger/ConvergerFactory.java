package gov.va.sparkcql.pipeline.converger;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.pipeline.ComponentFactory;

public interface ConvergerFactory extends ComponentFactory {

    public Converger create(Configuration configuration);
}
