package gov.va.transpiler.jinja.node.unary;

import org.hl7.elm.r1.ExpressionDef;

import gov.va.transpiler.jinja.node.DisabledNode;
import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.printing.Segment.PrintType;

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
        var expressionFileSegment = new Segment();
        expressionFileSegment.setName(getCqlEquivalent().getName());
        expressionFileSegment.setPrintType(PrintType.File);
        var expressionContentsSegment = new Segment();
        expressionContentsSegment.setPrintType(PrintType.Inline);
        expressionFileSegment.addSegmentToBody(expressionContentsSegment);
        if (getChild().isSimpleValue()) {
            expressionContentsSegment.setHead("SELECT ");
            expressionContentsSegment.setTail(" _val");
        }
        expressionContentsSegment.addSegmentToBody(getChild().toSegment());
        return expressionFileSegment;
    }

    @Override
    public boolean isTable() {
        return getChild().isTable();
    }
}
