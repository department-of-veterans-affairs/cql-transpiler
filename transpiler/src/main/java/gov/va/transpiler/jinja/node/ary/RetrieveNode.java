package gov.va.transpiler.jinja.node.ary;

import org.hl7.elm.r1.Retrieve;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.UnsupportedChildNodeException;
import gov.va.transpiler.jinja.node.leaf.ValueSetRefNode;
import gov.va.transpiler.jinja.node.unary.Unary;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class RetrieveNode extends Unary<Retrieve> {

    public RetrieveNode(State state, Retrieve t) {
        super(state, t);
    }

    @Override
    public void addChild(TranspilerNode child) {
        if (child instanceof ValueSetRefNode) {
            super.addChild(child);
        } else {
            throw new UnsupportedChildNodeException(this, child);
        }
    }

    @Override
    public boolean isTable() {
        return true;
    }

    @Override
    public boolean isSimpleValue() {
        return false;
    }

    @Override
    public Segment toSegment() {
        var segment = new Segment();
        segment.setHead("?{{ retrieve(");
        var typeSegment = new Segment();
        typeSegment.setHead(getCqlEquivalent().getDataType().getLocalPart());
        segment.addChild(typeSegment);
        if (getChild() != null) {
            typeSegment.setTail(", ");
            segment.addChild(getChild().toSegment());
        }
        segment.setTail(") }}?");
        return segment;
    }
}
