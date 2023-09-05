package gov.va.sparkcql.mock;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.domain.Plan;
import gov.va.sparkcql.pipeline.compiler.Compiler;
import gov.va.sparkcql.pipeline.compiler.CompilerFactory;
import gov.va.sparkcql.pipeline.repository.cql.CqlSourceRepository;
import gov.va.sparkcql.types.QualifiedIdentifier;

import java.util.List;

public class MockCompilerFactory implements CompilerFactory {

    @Override
    public Compiler create(Configuration configuration, CqlSourceRepository cqlSourceRepository) {
        return new Compiler() {
            @Override
            public Plan compile(String... cqlText) {
                return null;
            }

            @Override
            public Plan compile(List<QualifiedIdentifier> cqlIdentifier) {
                return null;
            }

            @Override
            public Plan compile(List<QualifiedIdentifier> identifiedCql, String... anonymousCql) {
                return null;
            }
        };
    }
}
