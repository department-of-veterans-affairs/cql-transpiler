package gov.va.transpiler.jinja.node.trackable.element.expression;

import java.util.ArrayList;
import java.util.List;

import org.hl7.elm.r1.Expression;
import org.hl7.elm.r1.Query;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.UnsupportedChildNodeException;
import gov.va.transpiler.jinja.node.trackable.element.AliasedQuerySourceNode;
import gov.va.transpiler.jinja.node.trackable.element.ReturnClauseNode;
import gov.va.transpiler.jinja.node.trackable.element.SortClauseNode;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.printing.Segment.PrintType;
import gov.va.transpiler.jinja.state.State;

public class QueryNode extends ExpressionNode<Query> {

    public ExpressionNode where = null;
    public List<ReturnClauseNode> returnClauseList = new ArrayList<>();
    public List<SortClauseNode> sortClauseList = new ArrayList<>();

    public QueryNode(State state, Query cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public void addChild(TranspilerNode child) {
        if(child instanceof ReturnClauseNode) {
            returnClauseList.add((ReturnClauseNode) child);
        } else if (child instanceof SortClauseNode) {
            sortClauseList.add((SortClauseNode) child);
        } else if (child instanceof ExpressionNode && ((ExpressionNode) child).getCqlEquivalent() == getCqlEquivalent().getWhere()) {
            if (where == null) {
                where = (ExpressionNode) child;
            } else {
                throw new UnsupportedChildNodeException(this, child);
            }
        } else {
            super.addChild(child);
        }
    }

    @Override
    public Type getType() {
        return Type.TABLE;
    }

    @Override
    public Segment toSegment() {
        var enclosingSegment = new Segment(getName() + "(", ")", PrintType.Inline);
        var joinerSegment = new Segment(", ");

        // sources
        enclosingSegment.addChild(joinChildren(getChildren(), "[","]", "", "", ", "));
        enclosingSegment.addChild(joinerSegment);

        // returnList
        enclosingSegment.addChild(joinChildren(returnClauseList, "[","]", "", "", ", "));
        enclosingSegment.addChild(joinerSegment);

        // where
        enclosingSegment.addChild(where == null ? new Segment("none") : where.toSegment());
        enclosingSegment.addChild(joinerSegment);
    
        // order by
        enclosingSegment.addChild(joinChildren(sortClauseList, "[","]", "", "", ", "));

        return enclosingSegment;
    }
}
