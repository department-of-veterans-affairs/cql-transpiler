package gov.va.transpiler.bulk.sparksql.node;

public class ReturnClauseNode extends AbstractNodeOneChild {

    @Override
    public String asOneLine() {
        return getChild().asOneLine();
    }
}
