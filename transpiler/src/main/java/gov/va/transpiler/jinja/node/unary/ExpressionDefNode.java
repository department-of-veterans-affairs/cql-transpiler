package gov.va.transpiler.jinja.node.unary;

import org.hl7.elm.r1.ExpressionDef;

import gov.va.transpiler.jinja.node.DisabledNode;
import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.printing.Segment;

public class ExpressionDefNode extends Unary<ExpressionDef> {

    public ExpressionDefNode(ExpressionDef t) {
        super(t);
    }

    @Override
    public void addChild(TranspilerNode child) {
        if (!(child instanceof DisabledNode)) {
            super.addChild(child);
        }
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
            expressionContentsSegment = new Segment(getChild());
            expressionContentsSegment.setHead("SELECT ");
            expressionContentsSegment.setTail(" _val");
            expressionContentsSegment.addSegmentToBody(getChild().toSegment());
        } else {
            expressionContentsSegment = getChild().toSegment();
        }
        expressionFileSegment.addSegmentToBody(expressionContentsSegment);

        return expressionFileSegment;
    }

    @Override
    public boolean isTable() {
        return getChild().isTable();
    }

    @Override
    public PrintType getPrintType() {
        return PrintType.File;
    }

    @Override
    public String getReferenceName() {
        return getCqlEquivalent().getName();
    }
}
