package gov.va.transpiler.sparksql.node.unary;

import gov.va.transpiler.sparksql.node.ary.TypeSpecifierNode;

public class IntervalTypeSpecifier extends TypeSpecifierNode {

    @Override
    public String asOneLine() {
        if (getChildren().size() != 1) {
            // TODO
            throw new UnsupportedOperationException("We currently only support single-typed intervals");
        }
        return "Interval<" + getChildren().get(0).asOneLine() + ">";
    }
}
