package gov.va.sparkcql.planner;

import java.util.List;

import org.hl7.elm.r1.Library;

import gov.va.sparkcql.entity.Plan;

public class DefaultPlanner implements Planner {
    
    public Plan plan(List<Library> libraries) {
        var collector = new RetrieveCollector();

        var retrievalOperations = libraries.stream().flatMap(library -> {
            return collector.visitLibrary(library, null).stream();
        }).toList();

        return new Plan()
            .withRetrievalOperations(retrievalOperations);
    }
}