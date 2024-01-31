package gov.va.transpiler.jinja.node.expression;

import org.hl7.elm.r1.ExpressionRef;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;
import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.UnsupportedChildNodeException;
import gov.va.transpiler.jinja.node.unary.ExpressionDefNode;
import gov.va.transpiler.jinja.node.unsupported.DisabledNode;
import gov.va.transpiler.jinja.node.utilityinterfaces.ReferenceNode;

public class ExpressionRefNode extends ExpressionNode<ExpressionRef> implements ReferenceNode {

    public ExpressionRefNode(State state, ExpressionRef t) {
        super(state, t);
    }

    @Override
    public boolean isTable() {
        return ((ExpressionDefNode) getReferenceTo()).isTable();
    }

    @Override
    public boolean isSimpleValue() {
        return ((ExpressionDefNode) getReferenceTo()).isSimpleValue();
    }

    @Override
    public String referenceType() {
        return ExpressionDefNode.REFERENCE_TYPE;
    }

    @Override
    public String referenceName() {
        return getCqlEquivalent().getName();
    }

    @Override
    public Segment toSegment() {
        var segment = new Segment();
        segment.setHead(getCqlEquivalent().getName() + "()");
        return segment;
    }

    @Override
    public void addChild(TranspilerNode child) throws UnsupportedChildNodeException {
        if (!(child instanceof DisabledNode)) {
            throw new UnsupportedChildNodeException(this, child);
        }
    }   
}
