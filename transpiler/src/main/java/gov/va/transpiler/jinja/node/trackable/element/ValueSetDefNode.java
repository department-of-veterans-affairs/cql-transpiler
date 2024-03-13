package gov.va.transpiler.jinja.node.trackable.element;

import org.hl7.elm.r1.ValueSetDef;

import gov.va.transpiler.jinja.node.utilityinterfaces.ReferenceableNode;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.printing.Segment.PrintType;
import gov.va.transpiler.jinja.state.State;

public class ValueSetDefNode extends ElementNode<ValueSetDef> implements ReferenceableNode {

    public static final String REFERENCE_TYPE = "ValueSetDef";

    public ValueSetDefNode(State state, ValueSetDef t) {
        super(state, t);
    }

    @Override
    public Segment toSegment() {
        var segment = new Segment("{% set " + referenceName() + " = '" + getCqlEquivalent().getId(),"' %}", PrintType.Line);
        segment.setLocator(getCqlEquivalent().getLocator());
        return segment;
    }

    @Override
    public String referenceType() {
        return REFERENCE_TYPE;
    }

    @Override
    public String referenceName() {
        return getCqlEquivalent().getName().replace(' ', '_');
    }

    @Override
    public int allowedNumberOfChildren() {
        return 0;
    }
}
