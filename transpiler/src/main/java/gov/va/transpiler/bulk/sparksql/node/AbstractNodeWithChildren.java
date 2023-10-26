package gov.va.transpiler.bulk.sparksql.node;

import java.util.ArrayList;
import java.util.List;

import gov.va.transpiler.node.OutputNode;

public abstract class AbstractNodeWithChildren extends AbstractCQLNode {

    private List<AbstractCQLNode> children = new ArrayList<>();

    @Override
    public boolean addChild(OutputNode child) {
        if (child instanceof AbstractCQLNode) {
            children.add((AbstractCQLNode) child);
            return true;
        }
        return false;
    }

    protected List<AbstractCQLNode> getChildren() {
        return children;
    }

    public String childAsOneLineCompressedIfTable(AbstractCQLNode child) {
        if (child.isTable()) {
            return "SELECT collect_list(*) AS _val FROM (SELECT struct(*) FROM (" + child.asOneLine() + "))";
        }
        return child.asOneLine();
    }
}
