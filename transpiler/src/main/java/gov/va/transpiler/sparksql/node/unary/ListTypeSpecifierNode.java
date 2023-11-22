package gov.va.transpiler.sparksql.node.unary;

import gov.va.transpiler.sparksql.node.ary.TypeSpecifierNode;

public class ListTypeSpecifierNode extends TypeSpecifierNode {

    @Override
    public String asOneLine() {
        String childrenNames = getChildren().get(0).asOneLine();
        for (int i = 1; i < getChildren().size(); i++) {
            childrenNames += ", " + getChildren().get(i).asOneLine();
        }
        return "List<" + childrenNames + ">";
    }
}
