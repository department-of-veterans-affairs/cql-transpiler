package gov.va.sparkcql.pipeline.planner;

import java.util.ArrayList;
import java.util.List;

import org.cqframework.cql.elm.tracking.Trackable;
import org.cqframework.cql.elm.visiting.ElmBaseLibraryVisitor;
import org.hl7.elm.r1.Retrieve;

public class RetrieveCollector extends ElmBaseLibraryVisitor<List<Retrieve>, Retrieve> {

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