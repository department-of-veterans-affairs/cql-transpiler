package gov.va.transpiler.jinja.node.trackable.element.expressiondef;

import java.util.Map;

import org.hl7.elm.r1.ExpressionDef;

import gov.va.transpiler.jinja.node.TranspilerNode;
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
    public TranspilerNode getChildByReference(String nameOrIndex) {
        return getChild().getChildByReference(nameOrIndex);
    }

    @Override
    public int allowedNumberOfChildren() {
        return 1;
    }

    @Override
    protected Map<String, String> getSimpleArgumentMap() {
        var map = super.getSimpleArgumentMap();
        map.put("'context'", "'" + getCqlEquivalent().getContext() + "'");
        map.put("'name'", "'" + getCqlEquivalent().getName() + "'");
        return map;
    }

    @Override
    public Segment toSegment() {
        // Wrap the dictionary representation of this object in a macro block for calling
        var segment = new Segment("{% macro " + referenceName() + "(state) %}{{ " + Standards.macroFileName() + ".OperatorHandler.print(state, ", ") }}{% endmacro %}", PrintType.Inline);
        segment.addChild(super.toSegment());
        segment.setPrintType(PrintType.Line);
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
    public String getTargetFileLocation() {
        return super.getTargetFileLocation() + Standards.FOLDER_SEPARATOR +  referenceName();
    }
}
