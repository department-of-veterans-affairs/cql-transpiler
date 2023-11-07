package gov.va.transpiler.bulk.sparksql.node;

import java.util.ArrayList;
import java.util.List;

import org.cqframework.cql.elm.tracking.Trackable;

import gov.va.transpiler.node.OutputNode;

public abstract class AbstractNodeWithChildren<T extends Trackable> extends AbstractCQLNode<T> {

    private List<AbstractCQLNode<? extends Trackable>> children = new ArrayList<>();

    @Override
    public boolean addChild(OutputNode<? extends Trackable> child) {
        if (child instanceof AbstractCQLNode) {
            children.add((AbstractCQLNode<? extends Trackable>) child);
            return true;
        }
        return false;
    }

    protected List<AbstractCQLNode<? extends Trackable>> getChildren() {
        return children;
    }

    public String childAsOneLineCompressedIfTable(AbstractCQLNode<? extends Trackable> child) {
        if (child.isTable()) {
            return "SELECT collect_list(struct(*)) AS _val FROM (" + child.asOneLine() + ")";
        }
        return child.asOneLine();
    }
}
