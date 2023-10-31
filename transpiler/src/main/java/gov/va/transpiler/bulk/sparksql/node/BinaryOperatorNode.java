package gov.va.transpiler.bulk.sparksql.node;

public class BinaryOperatorNode extends AbstractNodeBinaryExpression {

    public BinaryOperatorNode(String operator) {
        setName(operator);
    }

    @Override
    public String asOneLine() {
        return "SELECT leftval " + getName() + " rightval AS _val FROM ((SELECT _val AS leftval FROM (" + getChild1().asOneLine() + ")) OUTER JOIN (SELECT _val AS rightval FROM (" + getChild2().asOneLine() + ")))";
    }
    
}
