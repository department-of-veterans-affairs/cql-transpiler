package gov.va.transpiler.jinja.node.trackable.element.expression;

import org.hl7.elm.r1.Property;

import gov.va.transpiler.jinja.state.State;
import gov.va.transpiler.jinja.printing.Segment;

public class PropertyNode extends ExpressionNode<Property> {

    private String context;

    public PropertyNode(State state, Property cqlEquivalent) {
        super(state, cqlEquivalent);
        context = state.getContext();
    }

    @Override
    public Type getType() {
        if (getChild() == null && getCqlEquivalent().getScope() != null || getChild().getType() == Type.COLUMN_REFERENCE) {
            return Type.COLUMN_REFERENCE;
        } else {
            var childType = getChild().getChildByReference(getCqlEquivalent().getPath()).getType();
            if (childType == Type.ENCAPSULATED_SIMPLE) {
                return Type.ENCAPSULATED_SIMPLE;
            } else if (childType == Type.COLLECTED_TABLE) {
                return Type.TABLE;
            } else {
                throw new RuntimeException("unhandled property node type");
            }
        }
    }

    @Override
    public Segment toSegment() {
        var segment = new Segment();
        segment.setHead(getName() + "(");
        // property type
        var propertyTypeSegment = new Segment();
        propertyTypeSegment.setHead("'" + getType() + "', ");
        segment.addChild(propertyTypeSegment);
        // scope
        var scopeSegment = new Segment();
        scopeSegment.setHead(getCqlEquivalent().getScope() == null ? "none, " : "'" + getCqlEquivalent().getScope() + "', ");
        segment.addChild(scopeSegment);
        // path
        var pathSegment = new Segment();
        pathSegment.setHead(getCqlEquivalent().getPath() == null ? "none, " : "'" + getCqlEquivalent().getPath() + "', ");
        segment.addChild(pathSegment);
        // child
        if (getChild() == null) {
            var childSegment = new Segment();
            childSegment.setHead("none");
            segment.addChild(childSegment);
        } else {
            segment.addChild(childToSegment(getChild()));
        }
        segment.setTail(")");
        return getType() == Type.TABLE ? decollectSegment(context, segment) : segment;
    }
}
