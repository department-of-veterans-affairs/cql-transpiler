package gov.va.transpiler.bulk.sparksql.node;

import org.cqframework.cql.elm.tracking.Trackable;

public abstract class AbstractCQLAggregator<T extends Trackable> extends AbstractNodeOneChild<T> {

    @Override
    public boolean isTable() { 
        return getChild().isTable();
    }
}
