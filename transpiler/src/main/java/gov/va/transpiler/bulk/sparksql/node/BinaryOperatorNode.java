package gov.va.transpiler.bulk.sparksql.node;

public class BinaryOperatorNode extends AbstractNodeBinaryExpression {

    public BinaryOperatorNode(String operator) {
        setName(operator);
    }

    @Override
    public String asOneLine() {
        return "SELECT " + "(" + getChild1().asOneLine() + ") " + getName() + " (" + getChild2().asOneLine() + ") _val";
    }
}