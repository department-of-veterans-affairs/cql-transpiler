package gov.va.transpiler.jinja.node.trackable.element.expressiondef;

import java.util.Map;

import org.hl7.elm.r1.ExpressionDef;

import gov.va.transpiler.jinja.node.trackable.element.ElementNode;
import gov.va.transpiler.jinja.node.trackable.element.LibraryNode;
import gov.va.transpiler.jinja.node.utilityinterfaces.DirectPrint;
import gov.va.transpiler.jinja.node.utilityinterfaces.ReferenceableNode;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.printing.Segment.PrintType;
import gov.va.transpiler.jinja.standards.Standards;
import gov.va.transpiler.jinja.state.State;

public class ExpressionDefNode<T extends ExpressionDef> extends ElementNode<T> implements ReferenceableNode, DirectPrint {

    private final LibraryNode enclosingLibrary;

    public ExpressionDefNode(State state, T cqlEquivalent) {
        super(state, cqlEquivalent);
        enclosingLibrary = state.getCurrentLibrary();
        enclosingLibrary.addNamedChild(referenceName(), this);
        state.addCurrentExpressionToModelTracking(this);
    }

    @Override
    public int allowedNumberOfChildren() {
        return 1;
    }

    @Override
    protected Map<String, String> getLiteralArgumentMap() {
        var map = super.getLiteralArgumentMap();
        map.put("'context'", "'" + getCqlEquivalent().getContext() + "'");
        map.put("'referenceName'", "'" + referenceName() + "'");
        return map;
    }

    @Override
    public Segment toSegmentWrapped() {
        var enclosingSegment = new Segment("");
        enclosingSegment.setPrintType(PrintType.Line);
        enclosingSegment.setLocator(getCqlEquivalent().getLocator());
        // macro segment
        var macro = new Segment("{% macro " + sanitizeNameForJinja(referenceName()) + "(" + Standards.ENVIRONMENT_NAME + ", " + Standards.STATE_NAME + ") %}", "{% endmacro %}", PrintType.Inline);
        enclosingSegment.addChild(macro);
        // internal segment -- wrap the dictionary representation of this object
        String head = "{{ " + Standards.ENVIRONMENT_NAME + "." + Standards.OPERATOR_HANDLER_NAME + "." + Standards.PRINT_FUNCTION_NAME + "(" + Standards.ENVIRONMENT_NAME + ", " + Standards.ENVIRONMENT_NAME + "." + Standards.OPERATOR_HANDLER_NAME + ", " + Standards.STATE_NAME + ", ";
        String tail = ") }}";
        var internal = new Segment(head, tail, PrintType.Inline);
        internal.addChild(toSegment());
        macro.addChild(internal);
        return enclosingSegment;
    }

    @Override
    public String referenceName() {
        return enclosingLibrary.getCqlEquivalent().getIdentifier().getId() + getCqlEquivalent().getName();
    }

    @Override
    public String getTargetFileLocation() {
        return sanitizeNameForJinja(super.getTargetFileLocation() + Standards.FOLDER_SEPARATOR +  referenceName());
    }
}
