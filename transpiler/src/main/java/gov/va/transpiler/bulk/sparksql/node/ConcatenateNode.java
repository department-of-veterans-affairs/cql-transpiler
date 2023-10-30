package gov.va.transpiler.bulk.sparksql.node;

public class ConcatenateNode extends AbstractNodeBinaryExpression {

    @Override
    public String asOneLine() {
        return "SELECT concat(leftval, rightval) AS _val FROM ((SELECT _val AS leftval FROM " + getChild1().asOneLine() + ") OUTER JOIN ((SELECT _val AS rightval FROM " + getChild1().asOneLine() + "))))";
    }
    
}
