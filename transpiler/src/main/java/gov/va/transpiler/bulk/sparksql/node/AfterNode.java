package gov.va.transpiler.bulk.sparksql.node;

import org.hl7.elm.r1.After;

public class AfterNode extends AbstractNodeBinaryExpression<After> {

    @Override
    public String asOneLine() {
       return "(" + getChild1().asOneLine() + ") > (" + getChild2().asOneLine() + ")";
    }
}
