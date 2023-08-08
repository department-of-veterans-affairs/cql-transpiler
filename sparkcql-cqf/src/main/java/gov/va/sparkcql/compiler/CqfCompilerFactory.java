package gov.va.sparkcql.compiler;

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
