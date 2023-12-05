package gov.va.transpiler.jinja.node.unary;

import org.hl7.elm.r1.ExpressionDef;

import gov.va.transpiler.jinja.printing.Segment;

public class ExpressionDefNode extends Unary<ExpressionDef> {

    public ExpressionDefNode(ExpressionDef t) {
        super(t);
    }

    String expressionName;

    @Override
    public Segment toSegment() {
        var segment = new Segment();
        if (getChild().isTable()) {   
            segment.setHead("{% macro " + expressionName + " %}");
            segment.setTail("{% endmacro -%}");
        } else {
            segment.setHead("{% set " + expressionName + " = ");
            segment.setTail("-%}");
        }
        segment.setFileName(expressionName);
        segment.setToPrintInOwnFile();
        return segment;
    }

    @Override
    public boolean isTable() {
        return getChild().isTable();
    }
}
