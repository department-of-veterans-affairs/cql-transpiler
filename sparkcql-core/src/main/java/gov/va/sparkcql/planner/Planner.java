package gov.va.sparkcql.planner;

import java.util.List;

import org.hl7.elm.r1.Library;

import gov.va.sparkcql.model.RetrievalPlan;

public class Planner {
    
    public RetrievalPlan plan(List<Library> libraries) {
        var collector = new RetrieveAnalyzer();

        var retrievalOperations = libraries.stream().flatMap(library -> {
            return collector.visitLibrary(library, null).stream();
        }).toList();

        return new RetrievalPlan()
            .withRetrievalOperations(retrievalOperations);
    }
}