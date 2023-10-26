package gov.va.transpiler.bulk.sparksql.node;

import gov.va.transpiler.node.OutputNode;

public class QueryNode extends AbstractNodeWithChildren {

    private AliasedQuerySourceNode aliasedQuerySourceNode;
    private ReturnClauseNode returnClauseNode;

    @Override
    public boolean addChild(OutputNode child) {
        if (child instanceof AliasedQuerySourceNode) {
            aliasedQuerySourceNode = (AliasedQuerySourceNode) child;
            return true;
        } else if (child instanceof ReturnClauseNode) {
            returnClauseNode = (ReturnClauseNode) child;
            return true;
        }
        return super.addChild(child);
    }
    @Override
    public boolean isTable() {
        return true;
    }

    @Override
    public String asOneLine() {
        return "SELECT " + (returnClauseNode == null ? '*' : returnClauseNode.asOneLine()) + " FROM (" + aliasedQuerySourceNode.asOneLine() + "))";
    }
}
