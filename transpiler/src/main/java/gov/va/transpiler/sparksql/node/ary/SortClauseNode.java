package gov.va.transpiler.sparksql.node.ary;

import gov.va.transpiler.sparksql.node.AbstractCQLNode;
import gov.va.transpiler.sparksql.node.Ary;

public class SortClauseNode extends Ary {

    @Override
    public boolean addChild(AbstractCQLNode child) {
        return super.addChild(child);
    }


    @Override
    public String asOneLine() {
        String elements = getChildren().stream().map(AbstractCQLNode::asOneLine).reduce("", (a, b) -> {
            return a.isBlank() ? b : a + ", " + b;
        });
        return "ORDER BY " + elements;
    }
}
