package gov.va.transpiler.sparksql.node.ary;

import gov.va.transpiler.sparksql.node.Ary;

public class UnionNode extends Ary {

    @Override
    public String asOneLine() {
        var builder = "(" + getChildren().get(0).asOneLine() + ")";
        for (int i = 1; i < getChildren().size(); i++) {
            builder += " UNION (";
            builder += getChildren().get(0).asOneLine();
            builder += ")";
        }
        return builder;
    }
}
