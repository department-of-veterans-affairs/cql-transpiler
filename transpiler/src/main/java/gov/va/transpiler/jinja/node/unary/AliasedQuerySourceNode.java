package gov.va.transpiler.jinja.node.unary;

import org.hl7.elm.r1.AliasedQuerySource;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.leaf.RetrieveNode;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class AliasedQuerySourceNode extends Unary<AliasedQuerySource> {

    private PropertyNode propertyNode;

    public AliasedQuerySourceNode(State state, AliasedQuerySource t) {
        super(state, t);
    }

    @Override
    public void addChild(TranspilerNode child) {
        if (child instanceof PropertyNode) {
            propertyNode = (PropertyNode) child;
        } else {
            super.addChild(child);
        }
    }

    @Override
    public Segment toSegment() {
        var segment = new Segment();
        if (propertyNode == null) {
            if (getChild() instanceof RetrieveNode) {
                segment.addChild(getChild().toSegment());
                segment.setTail(" AS " + getCqlEquivalent().getAlias());
            } else {
                segment.setHead("SELECT * FROM (");
                segment.addChild(getChild().toSegment());
                segment.setTail(") AS " + getCqlEquivalent().getAlias());
            }
        } else {
            // TODO
            throw new UnsupportedOperationException("TODO");
        }
        return segment;
    }    
}
