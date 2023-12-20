package gov.va.transpiler.jinja.node.unary;

import org.hl7.elm.r1.ExpressionDef;

import gov.va.transpiler.jinja.node.ReferenceableNode;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.printing.Segment.PrintType;
import gov.va.transpiler.jinja.standards.Standards;
import gov.va.transpiler.jinja.state.State;

public class ExpressionDefNode extends Unary<ExpressionDef> implements ReferenceableNode {

    public static final String REFERENCE_TYPE = "ExpressionDef";

    public ExpressionDefNode(State state, ExpressionDef t) {
        super(state, t);
    }

    @Override
    public boolean isSimpleValue() {
        return false;
    }

    @Override
    public Segment toSegment() {
        var expressionFileSegment = new Segment();
        expressionFileSegment.setPrintType(PrintType.File);
        expressionFileSegment.setFileLocation(getTargetFileLocation());
        expressionFileSegment.setLocator(getCqlEquivalent().getLocator());

        Segment expressionContentsSegment;
        if (getChild().isSimpleValue()) {
            expressionContentsSegment = containerizer.containerizeSimpleValue(getChild());
        } else {
            expressionContentsSegment = getChild().toSegment();
        }
        expressionFileSegment.addChild(expressionContentsSegment);

        return expressionFileSegment;
    }

    @Override
    public String referenceType() {
        return REFERENCE_TYPE;
    }

    @Override
    public String referenceName() {
        return getCqlEquivalent().getName();
    }

    @Override
    public String getTargetFileLocation() {
        return super.getTargetFileLocation() + Standards.FOLDER_SEPARATOR +  referenceName();
    }
}
