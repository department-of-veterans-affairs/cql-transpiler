package gov.va.sparkcql.compiler;

import gov.va.sparkcql.repository.CqlSourceRepository;

public class CqfCompilerFactory implements CompilerFactory {

    @Override
    public Compiler create() {
        return new CqfCompiler();
    }

    @Override
    public Compiler create(CqlSourceRepository cqlSourceRepository) {
        return new CqfCompiler(cqlSourceRepository);
    }
}
