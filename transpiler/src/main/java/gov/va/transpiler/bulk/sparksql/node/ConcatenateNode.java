package gov.va.transpiler.bulk.sparksql.node;

import org.hl7.elm.r1.Concatenate;

public class ConcatenateNode extends AbstractNodeBinaryExpression<Concatenate> {

    @Override
    public String asOneLine() {
        return "SELECT concat(leftval, rightval) AS _val FROM ((SELECT _val AS leftval FROM " + getChild1().asOneLine() + ") OUTER JOIN ((SELECT _val AS rightval FROM " + getChild1().asOneLine() + "))))";
    }
    
}
