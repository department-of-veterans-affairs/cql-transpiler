package gov.va.transpiler.sparksql.node.ary;

import gov.va.transpiler.sparksql.node.AbstractCQLNode;
import gov.va.transpiler.sparksql.node.Ary;
import gov.va.transpiler.sparksql.node.unary.AliasedQuerySourceNode;
import gov.va.transpiler.sparksql.node.unary.ReturnClauseNode;

public class QueryNode extends Ary {

    private AliasedQuerySourceNode aliasedQuerySourceNode;
    private ReturnClauseNode returnClauseNode;
    private WhereNode whereNode;

    @Override
    public boolean addChild(AbstractCQLNode child) {
        if (child instanceof AliasedQuerySourceNode) {
            aliasedQuerySourceNode = (AliasedQuerySourceNode) child;
            return true;
        } else if (child instanceof ReturnClauseNode) {
            returnClauseNode = (ReturnClauseNode) child;
            return true;
        } else if (child instanceof WhereNode) {
            whereNode = (WhereNode) child;
            return true;
        }
        super.addChild(child);
        return false;
    }

    @Override
    public boolean isTable() {
        return true;
    }

    @Override
    public String asOneLine() {
        return "SELECT " + (returnClauseNode == null ? '*' : returnClauseNode.asOneLine())
            + " FROM (" + aliasedQuerySourceNode.asOneLine() + ")"
            + (whereNode == null ? "" : " " + whereNode.asOneLine());
    }
}
