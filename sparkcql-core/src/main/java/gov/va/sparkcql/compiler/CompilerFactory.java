package gov.va.sparkcql.compiler;

import gov.va.sparkcql.common.di.Factory;
import gov.va.sparkcql.repository.CqlSourceRepository;

public interface CompilerFactory extends Factory<Compiler> {

    public Compiler create();

    public Compiler create(CqlSourceRepository cqlSourceRepository);
}