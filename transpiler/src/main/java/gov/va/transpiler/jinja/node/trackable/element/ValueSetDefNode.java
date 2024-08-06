package gov.va.transpiler.jinja.node.trackable.element;

import java.util.Map;

import org.hl7.elm.r1.ValueSetDef;

import gov.va.transpiler.jinja.node.utilityinterfaces.DirectPrint;
import gov.va.transpiler.jinja.node.utilityinterfaces.ReferenceableNode;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.printing.Segment.PrintType;
import gov.va.transpiler.jinja.state.State;

public class ValueSetDefNode extends ElementNode<ValueSetDef> implements ReferenceableNode, DirectPrint {

    public static final String REFERENCE_TYPE = "ValueSetDef";
    private final LibraryNode enclosingLibrary;

    public ValueSetDefNode(State state, ValueSetDef t) {
        super(state, t);
        enclosingLibrary = state.getCurrentLibrary();
        enclosingLibrary.addNamedChild(referenceName(), this);
    }

    @Override
    public Segment toSegmentWrapped() {
        var segment = new Segment("{% macro " + sanitizeNameForJinja(referenceName()) + "() %}","{% endmacro %}", PrintType.Line);
        segment.addChild(toSegment());
        segment.setLocator(getCqlEquivalent().getLocator());
        return segment;
    }

    @Override
    protected Map<String, String> getLiteralArgumentMap() {
        var map = super.getLiteralArgumentMap();
        map.put("'value'", "'" + getCqlEquivalent().getId() + "'");
        map.put("'referenceName'", "'" + referenceName() + "'");
        return map;
    }

    @Override
    public String referenceName() {
        return enclosingLibrary.referenceName() + getCqlEquivalent().getName();
    }

    @Override
    public int allowedNumberOfChildren() {
        return 0;
    }
}
