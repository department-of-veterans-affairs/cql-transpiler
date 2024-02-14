package gov.va.transpiler.jinja.node.trackable.element.expression.aggregateexpression;

import org.hl7.elm.r1.AggregateExpression;

import gov.va.transpiler.jinja.node.trackable.element.expression.ExpressionNode;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.printing.Segment.PrintType;
import gov.va.transpiler.jinja.state.State;

public class AggregateExpressionNode<T extends AggregateExpression> extends ExpressionNode<T> {

    private String context;

    public AggregateExpressionNode(State state, T cqlEquivalent) {
        super(state, cqlEquivalent);
        context = state.getContext();
    }

    protected String getContext() {
        return context;
    }

    @Override
    public Type getType() {
        return Type.ENCAPSULATED_SIMPLE;
    }

    @Override
    public Segment toSegment() {
        var enclosingSegment = new Segment(getName() + "(", ")", PrintType.Inline);
        enclosingSegment.addChild(new Segment("'" + getContext() + "', "));
        enclosingSegment.addChild(getChild().toSegment());
        return enclosingSegment;
    }
}
