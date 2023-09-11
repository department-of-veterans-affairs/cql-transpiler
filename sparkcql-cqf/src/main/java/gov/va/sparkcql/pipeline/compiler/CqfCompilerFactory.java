package gov.va.sparkcql.pipeline.compiler;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.pipeline.repository.cql.CqlSourceRepository;

public class CqfCompilerFactory implements CompilerFactory {

    @Override
    public Compiler create(Configuration configuration, CqlSourceRepository cqlSourceRepository) {
        return new CqfCompiler(cqlSourceRepository);
    }
}