package gov.va.transpiler.bulk.sparksql.node;

public class AfterNode extends AbstractNodeBinaryExpression {

    @Override
    public String asOneLine() {
       return "(" + getChild1().asOneLine() + ") > (" + getChild2().asOneLine() + ")";
    }
}
