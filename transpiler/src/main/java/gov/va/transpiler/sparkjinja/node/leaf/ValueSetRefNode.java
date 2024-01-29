package gov.va.transpiler.sparkjinja.node.leaf;

import org.hl7.elm.r1.ValueSetRef;

import gov.va.transpiler.sparkjinja.node.ReferenceNode;
import gov.va.transpiler.sparkjinja.printing.Segment;
import gov.va.transpiler.sparkjinja.state.State;

public class ValueSetRefNode extends Leaf<ValueSetRef> implements ReferenceNode{

    public ValueSetRefNode(State state, ValueSetRef t) {
        super(state, t);
    }

    @Override
    public String referenceType() {
        return ValueSetDefNode.REFERENCE_TYPE;
    }

    @Override
    public String referenceName() {
        return getCqlEquivalent().getName();
    }

    @Override
    public boolean isTable() {
        return false;
    }

    @Override
    public boolean isSimpleValue() {
        return false;
    }

    @Override
    public Segment toSegment() {
        ValueSetDefNode valueSetDefNode = (ValueSetDefNode) getReferenceTo();
        var segment = new Segment();
        segment.setHead(valueSetDefNode.getCqlEquivalent().getId());
        return segment;
    }
}
