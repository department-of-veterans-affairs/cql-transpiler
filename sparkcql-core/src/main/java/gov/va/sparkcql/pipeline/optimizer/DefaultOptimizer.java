package gov.va.sparkcql.pipeline.optimizer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import gov.va.sparkcql.domain.Retrieval;
import org.cqframework.cql.elm.tracking.Trackable;
import org.cqframework.cql.elm.visiting.ElmBaseLibraryVisitor;

import gov.va.sparkcql.domain.Plan;
import org.hl7.elm.r1.Retrieve;

public class DefaultOptimizer implements Optimizer {
    
    public Plan optimize(Plan plan) {
        var collector = new RetrieveCollector();

        var retrieves = plan.getLibraries().stream().flatMap(library -> {
            return collector.visitLibrary(library, null).stream();
        });

        var retrievals = retrieves.map(Retrieval::of).distinct().collect(Collectors.toList());

        return new Plan(plan)
                .withRetrieves(retrievals);
    }

    static class RetrieveCollector extends ElmBaseLibraryVisitor<List<Retrieve>, Retrieve> {

        @Override
        protected List<Retrieve> defaultResult(Trackable elm, Retrieve context) {
            return List.of();
        }

        @Override
        protected List<Retrieve> aggregateResult(List<Retrieve> aggregate, List<Retrieve> nextResult) {
            var combined = new ArrayList<Retrieve>(aggregate);
            if (nextResult != null) {
                combined.addAll(nextResult);
            }
            return combined;
        }

        @Override
        public List<Retrieve> visitRetrieve(Retrieve elm, Retrieve context) {
            return List.of(elm);
        }
    }
}