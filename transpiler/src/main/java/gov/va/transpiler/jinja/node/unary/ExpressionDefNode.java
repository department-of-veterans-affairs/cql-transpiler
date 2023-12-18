package gov.va.transpiler.jinja.node.unary;

import org.hl7.elm.r1.ExpressionDef;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class ExpressionDefNode extends Unary<ExpressionDef> {

    public ExpressionDefNode(State state, ExpressionDef t) {
        super(state, t);
    }

    @Override
    public boolean isSimpleValue() {
        return false;
    }

    @Override
    public Segment toSegment() {
        var expressionFileSegment = new Segment(this);
        expressionFileSegment.setLocator(getCqlEquivalent().getLocator());

        Segment expressionContentsSegment;
        if (getChild().isSimpleValue()) {
            expressionContentsSegment = containerizer.containerizeSimpleValue(getChild());
        } else {
            expressionContentsSegment = getChild().toSegment();
        }
        expressionFileSegment.addSegmentToBody(expressionContentsSegment);

        return expressionFileSegment;
    }

    @Override
    public String referenceIs() {
        return getCqlEquivalent().getName();
    }

    @Override
    public PrintType getPrintType() {
        return PrintType.File;
    }
}
