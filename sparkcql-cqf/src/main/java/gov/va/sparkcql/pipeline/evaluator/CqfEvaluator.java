package gov.va.sparkcql.pipeline.evaluator;

import gov.va.sparkcql.diagnostic.Stopwatch;
import gov.va.sparkcql.domain.EvaluatedContext;
import gov.va.sparkcql.domain.Retrieval;
import org.opencds.cqf.cql.engine.execution.CqlEngine;

import java.util.List;
import java.util.Map;

public class CqfEvaluator implements Evaluator {

    private CqlEngine cqlEngine;
    private DataProviderAdapter dataProviderAdapter;
    private LibraryCacheAdapter libraryCacheAdapter;

    public CqfEvaluator(CqlEngine cqlEngine, DataProviderAdapter dataProviderAdapter, LibraryCacheAdapter libraryCacheAdapter) {
        this.cqlEngine = cqlEngine;
        this.dataProviderAdapter = dataProviderAdapter;
        this.libraryCacheAdapter = libraryCacheAdapter;
    }

    @Override
    public EvaluatedContext evaluate(String contextElementId, Map<Retrieval, List<Object>> clinicalData) {
        if (this.libraryCacheAdapter.getPlan().getLibraries().size() > 1) {
            throw new UnsupportedOperationException();      // TODO: Change design to replace LibraryCollection with CompiledPlan, OptimizedPlan, etc and have it identify head nodes
        }

        var primaryLibrary = this.libraryCacheAdapter.getPlan().getLibrary(0).orElseThrow();
        this.dataProviderAdapter.setClinicalData(clinicalData);
        var watch = new Stopwatch();

        var result = this.cqlEngine.evaluate(primaryLibrary.getIdentifier());
        System.out.println(result.toString());
        return null;
    }
}