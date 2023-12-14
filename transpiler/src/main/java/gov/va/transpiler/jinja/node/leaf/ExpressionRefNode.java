package gov.va.transpiler.jinja.node.leaf;

import org.hl7.elm.r1.ExpressionRef;

import gov.va.transpiler.jinja.node.unary.ExpressionDefNode;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class ExpressionRefNode extends Leaf<ExpressionRef> {

    private final ExpressionDefNode referenceTo;

    public ExpressionRefNode(State state, ExpressionRef t) {
        super(state, t);
        var retrievedReference = state.getReferences().get(references());
        if (retrievedReference instanceof ExpressionDefNode) {
            referenceTo = (ExpressionDefNode) retrievedReference;
        } else {
            throw new IllegalStateException("No reference found for " + references());
        }
    }

    @Override
    public Segment toSegment() {
        var segment = new Segment(this);
        segment.setHead("{% include " + referenceTo.getScope() + " %}");
        return segment;
    }

    @Override
    public boolean isTable() {
        return referenceTo.isTable();
    }

    @Override
    public boolean isSimpleValue() {
        return referenceTo.isSimpleValue();
    }

    @Override
    public String references() {
        return getCqlEquivalent().getName();
    }
}
