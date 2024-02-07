package gov.va.transpiler.jinja.node.trackable.element.expression;

import org.hl7.elm.r1.Tuple;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.trackable.TupleElementNode;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class TupleNode extends ExpressionNode<Tuple> {

    public TupleNode(State state, Tuple cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public TranspilerNode getChildByReference(String nameOrIndex) {
        for (var child : getChildren()) {
            if (nameOrIndex.equals(((TupleElementNode) child).getCqlEquivalent().getName())) {
                return child.getChildByReference(nameOrIndex);
            }
        }
        return null;
    }

    @Override
    public Type getType() {
        return Type.ENCAPSULATED_SIMPLE;
    }

    @Override
    public Segment toSegment() {
        return joinChildren(getChildren(), getName() + "([", "])", "", "", ", ");
    }
}
