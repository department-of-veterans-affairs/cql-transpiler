package gov.va.transpiler.jinja.node.trackable.element.expression;

import org.hl7.elm.r1.Retrieve;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class RetrieveNode extends ExpressionNode<Retrieve> {

    public RetrieveNode(State state, Retrieve cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public int allowedNumberOfChildren() {
        return 2;
    }

    @Override
    public Type getType() {
        return Type.TABLE;
    }

    @Override
    public Segment toSegment() {
        var segment = new Segment();
        segment.setHead(getName() + "(");
        var modelTypeSegment = new Segment();
        modelTypeSegment.setHead("'" + getCqlEquivalent().getDataType().getNamespaceURI() + "'");
        modelTypeSegment.setTail(", ");
        segment.addChild(modelTypeSegment);
        var valueSetSegment = new Segment();
        valueSetSegment.setHead(getCqlEquivalent().getValueSetProperty() == null ? "none" : "'" + getCqlEquivalent().getValueSetProperty() + "'");
        valueSetSegment.setTail(", ");
        segment.addChild(valueSetSegment);
        var modelSegment = new Segment();
        modelSegment.setHead("'" + getCqlEquivalent().getTemplateId() + "'");
        segment.addChild(modelSegment);
        segment.setTail(")");
        return segment;
    }
}
