package gov.va.transpiler.bulk.sparksql.node;

import org.cqframework.cql.elm.tracking.Trackable;

import gov.va.transpiler.node.OutputNode;

public abstract class AbstractNodeNoChildren<T extends Trackable> extends AbstractCQLNode<T> {

    @Override
    public boolean addChild(OutputNode<? extends Trackable> child) {
        return false;
    }
}
