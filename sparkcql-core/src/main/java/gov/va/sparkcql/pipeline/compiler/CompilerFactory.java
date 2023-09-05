package gov.va.sparkcql.pipeline.compiler;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.pipeline.ComponentFactory;
import gov.va.sparkcql.pipeline.repository.cql.CqlSourceRepository;

public interface CompilerFactory extends ComponentFactory {
    
    public Compiler create(Configuration configuration, CqlSourceRepository cqlSourceRepository);
}
