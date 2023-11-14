package gov.va.transpiler.sparksql.node;

import java.util.ArrayList;
import java.util.List;

public abstract class Ary extends AbstractCQLNode {

    private List<AbstractCQLNode> children = new ArrayList<>();

    @Override
    public boolean addChild(AbstractCQLNode child) {
        if (child instanceof AbstractCQLNode) {
            children.add((AbstractCQLNode) child);
            return true;
        }
        return false;
    }

    protected List<AbstractCQLNode> getChildren() {
        return children;
    }
}
