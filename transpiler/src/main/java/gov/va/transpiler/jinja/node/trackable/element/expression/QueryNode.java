package gov.va.transpiler.jinja.node.trackable.element.expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hl7.elm.r1.Query;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.InvalidChildNodeException;
import gov.va.transpiler.jinja.node.trackable.element.LetClauseNode;
import gov.va.transpiler.jinja.node.trackable.element.ReturnClauseNode;
import gov.va.transpiler.jinja.node.trackable.element.SortClauseNode;
import gov.va.transpiler.jinja.state.State;

public class QueryNode extends ExpressionNode<Query> {

    public List<TranspilerNode> whereList = new ArrayList<>();
    public List<TranspilerNode> returnClauseNodeList = new ArrayList<>();
    public List<TranspilerNode> sortClauseNodeList = new ArrayList<>();
    public List<TranspilerNode> letClauseNodeList = new ArrayList<>();

    public QueryNode(State state, Query cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public void addChild(TranspilerNode child) {
        if(child instanceof ReturnClauseNode) {
            if (returnClauseNodeList.isEmpty()) {
                returnClauseNodeList.add((ReturnClauseNode) child);
            } else {
                throw new InvalidChildNodeException(this, child);
            }
        } else if (child instanceof SortClauseNode) {
            if (sortClauseNodeList.isEmpty()) {
                sortClauseNodeList.add((SortClauseNode) child);
            } else {
                throw new InvalidChildNodeException(this, child);
            }
        } else if (child instanceof ExpressionNode && ((ExpressionNode<?>) child).getCqlEquivalent() == getCqlEquivalent().getWhere()) {
            if (whereList.isEmpty()) {
                whereList.add((ExpressionNode<?>) child);
            } else {
                throw new InvalidChildNodeException(this, child);
            }
        } else if (child instanceof LetClauseNode) {
            letClauseNodeList.add((LetClauseNode) child);  
        } else {
            super.addChild(child);
        }
    }

    @Override
    protected Map<String, List<TranspilerNode>> getNodeListArgumentMap() {
        var map = super.getNodeListArgumentMap();
        map.put("'where'", whereList);
        map.put("'returnClause'", returnClauseNodeList);
        map.put("'sortClause'", sortClauseNodeList);
        map.put("'letClause'", letClauseNodeList);
        return map;
    }
}
