package gov.va.transpiler.jinja.node.trackable.element.expression;

import org.hl7.elm.r1.Property;

import gov.va.transpiler.jinja.state.State;
import gov.va.transpiler.jinja.node.TranspilerNode;
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
            if (childType == Type.SIMPLE || childType == Type.ENCAPSULATED_SIMPLE) {
                // Any simple node referenced by a Property will have been encapsulated by the point the property is called
                return Type.ENCAPSULATED_SIMPLE;
            } else if (childType == Type.TABLE || childType == Type.COLLECTED_TABLE) {
                // Any table referenced by a Property will have been collected by the point the property is called
                // ... and will then have to be decollected in this Property's toSegment function
                return Type.TABLE;
            } else {
                throw new RuntimeException("unhandled property node type");
            }
        }
    }

    @Override
    public TranspilerNode getChildByReference(String nameOrIndex) {
        // expand chained property calls
        return getChild().getChildByReference(getCqlEquivalent().getPath()).getChildByReference(nameOrIndex);
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
