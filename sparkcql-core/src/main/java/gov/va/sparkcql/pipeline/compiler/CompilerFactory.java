package gov.va.sparkcql.pipeline.compiler;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.pipeline.ComponentFactory;
import gov.va.sparkcql.pipeline.repository.cql.CqlSourceRepository;

public abstract class CompilerFactory extends ComponentFactory {

    public CompilerFactory(Configuration configuration) {
        super(configuration);
    }

    public abstract Compiler create(CqlSourceRepository cqlSourceRepository);
}
