package gov.va.transpiler.jinja.node.leaf;

import org.hl7.elm.r1.ValueSetDef;

import gov.va.transpiler.jinja.node.ReferenceableNode;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class ValueSetDefNode extends Leaf<ValueSetDef> implements ReferenceableNode {

    public static final String REFERENCE_TYPE = "ValueSetDef";

    public ValueSetDefNode(State state, ValueSetDef t) {
        super(state, t);
    }

    @Override
    public boolean isTable() {
        // Not printable
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isSimpleValue() {
        // Not printable
        throw new UnsupportedOperationException();
    }

    @Override
    public Segment toSegment() {
        // Not printable
        throw new UnsupportedOperationException();
    }

    @Override
    public String referenceType() {
        return REFERENCE_TYPE;
    }

    @Override
    public String referenceName() {
        return getCqlEquivalent().getName();
    }   
}
