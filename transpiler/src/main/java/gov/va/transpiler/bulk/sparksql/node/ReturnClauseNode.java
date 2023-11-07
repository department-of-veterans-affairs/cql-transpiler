package gov.va.transpiler.bulk.sparksql.node;

import org.hl7.elm.r1.ReturnClause;

public class ReturnClauseNode extends AbstractNodeOneChild<ReturnClause> {

    @Override
    public String asOneLine() {
        return getChild().asOneLine();
    }
}
