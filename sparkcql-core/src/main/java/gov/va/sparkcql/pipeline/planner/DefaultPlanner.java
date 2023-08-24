package gov.va.sparkcql.pipeline.planner;

import java.util.List;
import java.util.stream.Collectors;

import gov.va.sparkcql.domain.Retrieval;
import org.hl7.elm.r1.Library;

import gov.va.sparkcql.domain.Plan;

public class DefaultPlanner implements Planner {
    
    public Plan plan(List<Library> libraries) {
        var collector = new RetrieveCollector();

        var retrieves = libraries.stream().flatMap(library -> {
            return collector.visitLibrary(library, null).stream();
        }).distinct();

        var serDeRetrieves = retrieves.map(Retrieval::of).collect(Collectors.toList());

        return new Plan().withRetrieves(serDeRetrieves);
    }
}