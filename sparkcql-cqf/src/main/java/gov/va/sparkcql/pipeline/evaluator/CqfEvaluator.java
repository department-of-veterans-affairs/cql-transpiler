package gov.va.sparkcql.pipeline.evaluator;

import gov.va.sparkcql.diagnostic.Stopwatch;
import gov.va.sparkcql.domain.EvaluatedContext;
import gov.va.sparkcql.domain.LibraryCollection;
import gov.va.sparkcql.domain.Retrieval;
import gov.va.sparkcql.pipeline.repository.cql.CqlSourceRepository;
import org.cqframework.cql.cql2elm.CqlCompilerOptions;
import org.cqframework.cql.cql2elm.LibraryManager;
import org.cqframework.cql.cql2elm.ModelManager;
import org.cqframework.cql.cql2elm.model.CompiledLibrary;
import org.hl7.elm.r1.VersionedIdentifier;
import org.opencds.cqf.cql.engine.data.DataProvider;
import org.opencds.cqf.cql.engine.execution.CqlEngine;
import org.opencds.cqf.cql.engine.execution.Environment;
import org.opencds.cqf.cql.engine.terminology.TerminologyProvider;

import java.util.EnumSet;
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
        if (this.libraryCacheAdapter.getLibraryCollection().size() > 1) {
            throw new UnsupportedOperationException();      // TODO: Change design to replace LibraryCollection with CompiledPlan, OptimizedPlan, etc and have it identify head nodes
        }

        var primaryLibrary = this.libraryCacheAdapter.getLibraryCollection().get(0);
        this.dataProviderAdapter.setClinicalData(clinicalData);
        var watch = new Stopwatch();

        var result = this.cqlEngine.evaluate(primaryLibrary.getIdentifier());
        System.out.println(result.toString());
        return null;
    }
}