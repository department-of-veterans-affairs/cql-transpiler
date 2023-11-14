package gov.va.transpiler.sparksql.node.binary;

import gov.va.transpiler.sparksql.node.Binary;

public class BinaryOperatorNode extends Binary {

    public BinaryOperatorNode(String operator) {
        setName(operator);
    }

    @Override
    public String asOneLine() {
        String leftSide = getChild1().isEncapsulated() ? "(" + getChild1().asOneLine() + ")" : getChild1().asOneLine();
        String rightSide = getChild2().isEncapsulated() ? "(" + getChild2().asOneLine() + ")" : getChild2().asOneLine();
        String builder = leftSide + " " + getName() + " " + rightSide;
        return isEncapsulated() ? "SELECT " + builder + " _val" : builder;
    }
}
