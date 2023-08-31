package gov.va.sparkcql.pipeline.evaluator;

import gov.va.sparkcql.diagnostic.Stopwatch;
import gov.va.sparkcql.domain.EvaluatedContext;
import gov.va.sparkcql.domain.Retrieval;
import org.opencds.cqf.cql.engine.execution.CqlEngine;

import java.util.List;
import java.util.Map;

public class CqfEvaluator implements Evaluator {

    private final CqlEngine cqlEngine;
    private final List<MutableCompositeDataProvider> mutableDataProviders;
    private final LibraryCacheAdapter libraryCacheAdapter;

    public CqfEvaluator(CqlEngine cqlEngine, List<MutableCompositeDataProvider> mutableDataProviders, LibraryCacheAdapter libraryCacheAdapter) {
        this.cqlEngine = cqlEngine;
        this.mutableDataProviders = mutableDataProviders;
        this.libraryCacheAdapter = libraryCacheAdapter;
    }

    @Override
    public EvaluatedContext evaluate(String contextElementId, Map<Retrieval, List<Object>> clinicalData) {

        // Identify the primary library which is the library that includes all other libraries. The
        // CQF engine only accepts a single library for execution. Instead of iterating each library
        // we need to calculate and duplicating the calculation of some shared libraries, we ensure
        // all libraries are covered in one iteration.
        var primaryLibrary = this.libraryCacheAdapter.getPlan().getLibrary(0).orElseThrow();
        if (this.libraryCacheAdapter.getPlan().getLibraries().size() > 1) {
            // TODO: Change design to have plan identify head node or create a outermost CQL container
            throw new UnsupportedOperationException();
        }

        // We're provided clinical data for each context execution. Update the DataProvider
        // interfaces we gave to the CQF engine with the data for this iteration.
        var newRetrieveProviderAdapter = new RetrieveProviderAdapter(clinicalData);
        this.mutableDataProviders.forEach(dp -> dp.setRetrieveProviderAdapter(newRetrieveProviderAdapter));

        var result = this.cqlEngine.evaluate(primaryLibrary.getIdentifier());
        System.out.println(result.toString());
        return null;
    }
}