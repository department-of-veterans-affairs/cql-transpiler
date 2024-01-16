package gov.va.transpiler.sparksql.node.unary;

import gov.va.transpiler.sparksql.node.AbstractCQLNode;
import gov.va.transpiler.sparksql.node.Unary;
import gov.va.transpiler.sparksql.node.ary.TypeSpecifierNode;

public class AsNode extends Unary {

    @SuppressWarnings("unused")
    private TypeSpecifierNode typeSpecifierNode;

    @Override
    public boolean addChild(AbstractCQLNode child) {
        if (child instanceof TypeSpecifierNode) {
            typeSpecifierNode = (TypeSpecifierNode) child;
            return true;
        }
        return super.addChild(child);
    }

    @Override
    public String asOneLine() {
        return getChild().asOneLine();
    }
}
