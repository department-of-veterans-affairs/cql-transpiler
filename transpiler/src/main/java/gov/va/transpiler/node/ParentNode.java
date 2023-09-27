package gov.va.transpiler.node;

import java.util.ArrayList;
import java.util.List;

public abstract class ParentNode extends OutputNode {

    private List<OutputNode> children = new ArrayList<>();

    @Override
    public boolean addChild(OutputNode child) {
        children.add(child);
        return true;
    }

    protected List<OutputNode> getChildren() {
        return children;
    }
}
