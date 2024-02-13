package gov.va.transpiler.jinja.node.trackable.element.expression;

import org.hl7.elm.r1.Query;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.UnsupportedChildNodeException;
import gov.va.transpiler.jinja.node.trackable.element.ReturnClauseNode;
import gov.va.transpiler.jinja.node.trackable.element.SortClauseNode;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.printing.Segment.PrintType;
import gov.va.transpiler.jinja.state.State;

public class QueryNode extends ExpressionNode<Query> {

    public ExpressionNode<?> where = null;
    public ReturnClauseNode returnClause = null;
    public SortClauseNode sortClause  = null;

    public QueryNode(State state, Query cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public void addChild(TranspilerNode child) {
        if(child instanceof ReturnClauseNode) {
            if (returnClause == null) {
                returnClause = (ReturnClauseNode) child;
            } else {
                throw new UnsupportedChildNodeException(this, child);
            }
        } else if (child instanceof SortClauseNode) {
            if (sortClause == null) {
                sortClause = (SortClauseNode) child;
            } else {
                throw new UnsupportedChildNodeException(this, child);
            }
        } else if (child instanceof ExpressionNode && ((ExpressionNode<?>) child).getCqlEquivalent() == getCqlEquivalent().getWhere()) {
            if (where == null) {
                where = (ExpressionNode<?>) child;
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
        enclosingSegment.addChild(returnClause == null ? new Segment("none") : returnClause.toSegment());
        enclosingSegment.addChild(joinerSegment);

        // where
        enclosingSegment.addChild(where == null ? new Segment("none") : where.toSegment());
        enclosingSegment.addChild(joinerSegment);
    
        // order by
        enclosingSegment.addChild(sortClause == null ? new Segment("none") : sortClause.toSegment());

        return enclosingSegment;
    }
}
