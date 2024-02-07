package gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression.naryexpression;

import java.util.List;
import java.util.stream.Collectors;

import org.hl7.elm.r1.NaryExpression;

import gov.va.transpiler.jinja.node.trackable.element.expression.ExpressionNode;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class NaryExpressionNode<T extends NaryExpression> extends ExpressionNode<T> {

    public NaryExpressionNode(State state, T cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public Type getType() {
        return Type.SIMPLE;
    }

    @Override
    public Segment toSegment() {
        var enclosingSegment = new Segment();
        enclosingSegment.setHead(getName() + "(");
        enclosingSegment.addChild(joinChildren(getChildren(), "[", "], ", "", "", ", "));
        List<Segment> typeList = getChildren().stream().map(child -> child.getType()).map(type -> new Segment("'" + type.name() + "'")).collect(Collectors.toList());
        enclosingSegment.addChild(joinSegments(typeList, "[", "]", ", "));
        enclosingSegment.setTail(")");
        return enclosingSegment;
    }
}
