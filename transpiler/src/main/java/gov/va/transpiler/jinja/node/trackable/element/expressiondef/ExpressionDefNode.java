package gov.va.transpiler.jinja.node.trackable.element.expressiondef;

import java.util.Map;

import org.hl7.elm.r1.ExpressionDef;

import gov.va.transpiler.jinja.node.trackable.element.ElementNode;
import gov.va.transpiler.jinja.node.utilityinterfaces.ReferenceableNode;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.printing.Segment.PrintType;
import gov.va.transpiler.jinja.standards.Standards;
import gov.va.transpiler.jinja.state.State;

public class ExpressionDefNode<T extends ExpressionDef> extends ElementNode<T> implements ReferenceableNode {

    public static final String REFERENCE_TYPE = "ExpressionDef";

    public ExpressionDefNode(State state, T cqlEquivalent) {
        super(state, cqlEquivalent);
        state.setContext(getCqlEquivalent().getContext());
    }

    @Override
    public int allowedNumberOfChildren() {
        return 1;
    }

    @Override
    protected Map<String, String> getLiteralArgumentMap() {
        var map = super.getLiteralArgumentMap();
        map.put("'context'", "'" + getCqlEquivalent().getContext() + "'");
        map.put("'name'", "'" + getCqlEquivalent().getName() + "'");
        return map;
    }

    protected Segment nodeToDictionarySegment() {
        return super.toSegment();
    }

    @Override
    public Segment toSegment() {
        var enclosingSegment = new Segment("");
        enclosingSegment.setPrintType(PrintType.Line);
        enclosingSegment.setLocator(getCqlEquivalent().getLocator());
        // macro segment
        var macro = new Segment("{% macro " + referenceName() + "(state) %}", "{% endmacro %}", PrintType.Inline);
        enclosingSegment.addChild(macro);
        // internal segment - wrap the dictionary representation of this object
        var internal = new Segment("{{ " + Standards.macroFileName() + ".OperatorHandler.print(state, ", ") }}", PrintType.Inline);
        internal.addChild(nodeToDictionarySegment());
        macro.addChild(internal);
        return enclosingSegment;
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
    public String getTargetFileLocation() {
        return super.getTargetFileLocation() + Standards.FOLDER_SEPARATOR +  referenceName();
    }
}
