package gov.va.transpiler.jinja.node.trackable.element.expression;

import org.hl7.elm.r1.QueryLetRef;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.trackable.element.LetClauseNode;
import gov.va.transpiler.jinja.node.utilityinterfaces.ReferenceNode;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class QueryLetRefNode extends ExpressionNode<QueryLetRef> implements ReferenceNode {

    public QueryLetRefNode(State state, QueryLetRef cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public String referenceType() {
        return LetClauseNode.REFERENCE_TYPE;
    }

    @Override
    public Type getType() {
        return ((LetClauseNode) getReferenceTo()).getType();
    }

    @Override
    public String referenceName() {
        return getCqlEquivalent().getName();
    }

    @Override
    public TranspilerNode getChildByReference(String nameOrIndex) {
        return ((LetClauseNode) getReferenceTo()).getChildByReference(nameOrIndex);
    }

    @Override
    public Segment toSegment() {
        return new Segment(getName());
    }
}
