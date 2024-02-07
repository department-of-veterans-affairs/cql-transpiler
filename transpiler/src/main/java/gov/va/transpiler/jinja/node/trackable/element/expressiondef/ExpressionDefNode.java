package gov.va.transpiler.jinja.node.trackable.element.expressiondef;

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
    public Segment toSegment() {
        var segment = new Segment();
        segment.setHead("{% macro " + referenceName() + "() %}{{ ");
        segment.addChild(getChild().toSegment());
        segment.setTail(" }}{% endmacro %}");
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

    @Override
    public Type getType() {
        return getChild().getType();
    }
}
