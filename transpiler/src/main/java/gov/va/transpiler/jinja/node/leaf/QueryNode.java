package gov.va.transpiler.jinja.node.leaf;

import org.hl7.elm.r1.Query;

import gov.va.transpiler.jinja.node.CQLEquivalent;
import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.ary.SortClauseNode;
import gov.va.transpiler.jinja.node.unary.AliasedQuerySourceNode;
import gov.va.transpiler.jinja.node.unary.ReturnClauseNode;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class QueryNode extends Leaf<Query> {

    public AliasedQuerySourceNode aliasedQuerySourceNode;
    private ReturnClauseNode returnClauseNode;
    private CQLEquivalent<?> whereNode;
    private SortClauseNode sortNode;

    public QueryNode(State state, Query t) {
        super(state, t);
    }

    @Override
    public void addChild(TranspilerNode child) {
        if (child instanceof CQLEquivalent<?>) {
            var equivalent = (CQLEquivalent<?>) child;
            if (equivalent instanceof AliasedQuerySourceNode) {
                aliasedQuerySourceNode = (AliasedQuerySourceNode) equivalent;
            } else if (equivalent instanceof ReturnClauseNode) {
                returnClauseNode = (ReturnClauseNode) equivalent;
            } else if (equivalent.getCqlEquivalent() == getCqlEquivalent().getWhere()) {
                whereNode = equivalent;
            } else if (child instanceof SortClauseNode) {
                sortNode = (SortClauseNode) child;
            } else {
                super.addChild(child);
            }
        } else {
            super.addChild(child);
        }
    }

    @Override
    public boolean isSimpleValue() {
        return false;
    }

    @Override
    public boolean isTable() {
        return true;
    }

    @Override
    public Segment toSegment() {
        var segment = new Segment();

        // Return clause
        var selectSegment = new Segment();
        if (returnClauseNode == null) {
            selectSegment.setHead("SELECT *");
        } else {
            selectSegment.setHead("SELECT ");
            selectSegment.addChild(returnClauseNode.toSegment());
        }
        segment.addChild(selectSegment);

        // Query source
        var sourceSegment = new Segment();
        sourceSegment.setHead(" FROM (");
        sourceSegment.addChild(aliasedQuerySourceNode.toSegment());
        sourceSegment.setTail(")");
        segment.addChild(sourceSegment);

        // Where clause
        if (whereNode != null) {
            var whereSegment = new Segment();
            whereSegment.setHead(" WHERE (");
            whereSegment.addChild(whereNode.toSegment());
            whereSegment.setTail(")");
            segment.addChild(whereSegment);
        }

        // Sort Clause
        if (sortNode != null) {
            var sortSegment = new Segment();
            sortSegment.setHead(" ");
            sortSegment.addChild(sortNode.toSegment());
            segment.addChild(sortSegment);
        }

        return segment;
    }
}
