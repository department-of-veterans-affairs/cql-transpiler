package gov.va.transpiler.jinja.node.trackable.element.expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hl7.elm.r1.AliasedQuerySource;
import org.hl7.elm.r1.Query;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.CQLEquivalent;
import gov.va.transpiler.jinja.node.trackable.element.LetClauseNode;
import gov.va.transpiler.jinja.node.trackable.element.RelationshipClauseNode;
import gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression.AliasRefNode;
import gov.va.transpiler.jinja.state.State;

public class QueryNode extends ExpressionNode<Query> {

    public TranspilerNode whereNode;
    public TranspilerNode returnClauseNode;
    public TranspilerNode sortClauseNode;
    public List<TranspilerNode> letClauseNodeList = new ArrayList<>();
    public List<TranspilerNode> relationshipClauseNodeList = new ArrayList<>();
    public List<TranspilerNode> aliasedQuerySources = new ArrayList<>();
    public List<TranspilerNode> aliasRefNodeList = new ArrayList<>();

    public QueryNode(State state, Query cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    public void addAliasRefNode(AliasRefNode aliasRefNode) {
        aliasRefNodeList.add(aliasRefNode);
    }

    @Override
    public void addChild(TranspilerNode child) {
        if(child instanceof CQLEquivalent && ((CQLEquivalent<?>)child).getCqlEquivalent() == getCqlEquivalent().getReturn()) {
            returnClauseNode = child;
        } else if (child instanceof CQLEquivalent && ((CQLEquivalent<?>)child).getCqlEquivalent() == getCqlEquivalent().getSort()) {
            sortClauseNode = child;
        } else if (child instanceof CQLEquivalent && ((CQLEquivalent<?>)child).getCqlEquivalent() == getCqlEquivalent().getWhere()) {
            whereNode = child;
        } else if (child instanceof LetClauseNode) {
            letClauseNodeList.add((LetClauseNode) child);
        } else if (child instanceof CQLEquivalent && ((CQLEquivalent<?>) child).getCqlEquivalent() instanceof AliasedQuerySource) {
            if (child instanceof RelationshipClauseNode) {
                relationshipClauseNodeList.add((RelationshipClauseNode<?>) child);
            } else {
                aliasedQuerySources.add(child);
            }
        } else {
            super.addChild(child);
        }
        processChildDependencies(child);
    }

    @Override
    protected Map<String, TranspilerNode> getNodeArgumentMap() {
        var map = super.getNodeArgumentMap();
        map.put("'where'", whereNode);
        map.put("'returnClause'", returnClauseNode);
        map.put("'sortClause'", sortClauseNode);
        return map;
    }

    @Override
    protected Map<String, List<TranspilerNode>> getNodeListArgumentMap() {
        var map = super.getNodeListArgumentMap();
        map.put("'aliasedQuerySources'", aliasedQuerySources);
        map.put("'letClauseList'", letClauseNodeList);
        map.put("'relationshipClauseList'", relationshipClauseNodeList);
        map.put("'aliasRefNodeList'", aliasRefNodeList);
        return map;
    }
}
