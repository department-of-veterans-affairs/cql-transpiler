package gov.va.sparkcql.pipeline.compiler;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.pipeline.repository.cql.CqlSourceRepository;

public class CqfCompilerFactory extends CompilerFactory {

    public CqfCompilerFactory(Configuration configuration) {
        super(configuration);
    }

    @Override
    public Compiler create(CqlSourceRepository cqlSourceRepository) {
        return new CqfCompiler(cqlSourceRepository);
    }
}