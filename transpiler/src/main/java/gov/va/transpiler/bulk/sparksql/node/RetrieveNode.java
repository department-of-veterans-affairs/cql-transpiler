package gov.va.transpiler.bulk.sparksql.node;

public class RetrieveNode extends AbstractNodeNoChildren {

    @Override
    public String asOneLine() {
        return "SELECT * FROM " + getName();
    }
}
