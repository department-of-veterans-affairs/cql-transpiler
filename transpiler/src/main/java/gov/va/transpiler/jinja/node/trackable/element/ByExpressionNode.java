package gov.va.transpiler.jinja.node.trackable.element;

import org.hl7.elm.r1.ByExpression;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.printing.Segment.PrintType;
import gov.va.transpiler.jinja.state.State;

public class ByExpressionNode extends SortByItemNode<ByExpression> {

    public ByExpressionNode(State state, ByExpression cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public Segment toSegment() {
        var segment = new Segment(getName() + "(", ")", PrintType.Inline);
        segment.addChild(new Segment(getCqlEquivalent().getDirection() == null ? "none, " : "'" + getCqlEquivalent().getDirection().name() + "', "));
        segment.addChild(getChild().toSegment());
        return segment;
    }
}
