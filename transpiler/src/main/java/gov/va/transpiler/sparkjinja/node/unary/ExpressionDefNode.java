package gov.va.transpiler.sparkjinja.node.unary;

import org.hl7.elm.r1.ExpressionDef;

import gov.va.transpiler.sparkjinja.node.ReferenceableNode;
import gov.va.transpiler.sparkjinja.printing.Segment;
import gov.va.transpiler.sparkjinja.printing.Segment.PrintType;
import gov.va.transpiler.sparkjinja.standards.Standards;
import gov.va.transpiler.sparkjinja.state.State;

public class ExpressionDefNode extends Unary<ExpressionDef> implements ReferenceableNode {

    public static final String REFERENCE_TYPE = "ExpressionDef";
    public static final String UNFILTERED_CONTEXT = "Unfiltered";
    public ExpressionDefNode(State state, ExpressionDef t) {
        super(state, t);
        if (UNFILTERED_CONTEXT.equalsIgnoreCase(getCqlEquivalent().getContext())) {
            state.setContext(null);
        } else {
            state.setContext(getCqlEquivalent().getContext());
        }
    }

    @Override
    public boolean isSimpleValue() {
        return false;
    }

    @Override
    public Segment toSegment() {
        Segment expressionContentsSegment;
        if (getChild().isSimpleValue()) {
            expressionContentsSegment = containerizer.containerizeSimpleValue(getChild());
        } else {
            expressionContentsSegment = getChild().toSegment();
        }
        expressionContentsSegment.setHead("{% macro " + referenceName() + "() %}");
        expressionContentsSegment.setTail("{% endmacro %}");
        expressionContentsSegment.setPrintType(PrintType.Line);
        expressionContentsSegment.setLocator(getCqlEquivalent().getLocator());

        return expressionContentsSegment;
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