package gov.va.sparkcql.planner;

import java.util.ArrayList;
import java.util.List;

import org.cqframework.cql.elm.tracking.Trackable;
import org.cqframework.cql.elm.visiting.ElmBaseLibraryVisitor;
import org.hl7.elm.r1.Retrieve;

import gov.va.sparkcql.entity.RetrievalOperation;

public class RetrieveCollector extends ElmBaseLibraryVisitor<List<RetrievalOperation>, RetrievalOperation> {

    @Override
    protected List<RetrievalOperation> defaultResult(Trackable elm, RetrievalOperation context) {
        return List.of();
    }

    @Override
    protected List<RetrievalOperation> aggregateResult(List<RetrievalOperation> aggregate, List<RetrievalOperation> nextResult) {
        var combined = new ArrayList<RetrievalOperation>(aggregate);
        if (nextResult != null) {
            combined.addAll(nextResult);
        }
        return combined;
    }

    @Override
    public List<RetrievalOperation> visitRetrieve(Retrieve elm, RetrievalOperation context) {
        return List.of(new RetrievalOperation().withRetrieve(elm));
    }
}