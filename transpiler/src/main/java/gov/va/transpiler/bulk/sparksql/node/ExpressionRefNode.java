package gov.va.transpiler.bulk.sparksql.node;

public class ExpressionRefNode extends AbstractNodeNoChildren {

    @Override
    public String asOneLine() {
        return "SELECT * FROM " + getName();
    }
}
