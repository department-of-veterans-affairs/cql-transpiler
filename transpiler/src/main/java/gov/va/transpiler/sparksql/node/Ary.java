package gov.va.transpiler.sparksql.node;

import static gov.va.transpiler.sparksql.utilities.Standards.SINGLE_VALUE_COLUMN_NAME;

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

    public String childAsOneLineCompressedIfTable(AbstractCQLNode child) {
        if (child.isTable()) {
            return "SELECT collect_list(struct(*)) AS " + SINGLE_VALUE_COLUMN_NAME + " FROM (" + child.asOneLine() + ")";
        }
        return child.asOneLine();
    }
}
