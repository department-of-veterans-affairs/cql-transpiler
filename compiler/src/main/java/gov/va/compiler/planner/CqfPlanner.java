package gov.va.compiler.planner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.cqframework.cql.elm.tracking.Trackable;
import org.cqframework.cql.elm.visiting.ElmBaseLibraryVisitor;

import org.hl7.elm.r1.Library;
import org.hl7.elm.r1.Retrieve;

import gov.va.compiler.domain.RetrieveDefinition;

public class CqfPlanner {

    public List<RetrieveDefinition> extractRetrieveDefinitions(List<Library> libraries) {
        var collector =new CqfPlanner.RetrieveCollector();

        var retrieves = libraries.stream().flatMap(library -> {
            return collector.visitLibrary(library, null).stream();
        });

        return retrieves.map(RetrieveDefinition::of).distinct().collect(Collectors.toList());
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