package gov.va.sparkcql.compiler;

import gov.va.sparkcql.common.di.Factory;

public interface CompilerFactory extends Factory<Compiler> {

    public Compiler create();

    public Compiler create(CqlSourceRepository cqlSourceRepository);
}