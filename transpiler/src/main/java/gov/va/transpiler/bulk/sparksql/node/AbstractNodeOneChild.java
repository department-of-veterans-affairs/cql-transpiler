package gov.va.transpiler.bulk.sparksql.node;

import org.cqframework.cql.elm.tracking.Trackable;

import gov.va.transpiler.node.OutputNode;

public abstract class AbstractNodeOneChild<T extends Trackable> extends AbstractNodeWithChildren<T> {

    @Override
    public boolean addChild(OutputNode<? extends Trackable> child) {
        if (getChildren().isEmpty()) {
            return super.addChild(child);
        }
        return false;
    }

    public boolean hasChild() {
        return !getChildren().isEmpty();
    }

    public AbstractCQLNode<? extends Trackable> getChild() {
        return getChildren().get(0);
    }

    @Override
    public boolean isTable() {
        return hasChild() && getChild().isTable();
    }
}
