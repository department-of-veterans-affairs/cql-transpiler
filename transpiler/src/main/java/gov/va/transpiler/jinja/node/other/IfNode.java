package gov.va.transpiler.jinja.node.other;

import org.hl7.elm.r1.If;

import gov.va.transpiler.jinja.node.CQLEquivalent;
import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.UnsupportedChildNodeException;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class IfNode extends CQLEquivalent<If> {

    private CQLEquivalent<?> condition;
    private CQLEquivalent<?> then;
    private CQLEquivalent<?> otherwise;

    public IfNode(State state, If t) {
        super(state, t);
    }

    @Override
    public void addChild(TranspilerNode child) {
        if (child instanceof CQLEquivalent) {
            var castChild = (CQLEquivalent<?>) child;
            var equivalent = castChild.getCqlEquivalent();
            if (equivalent == getCqlEquivalent().getCondition()) {
                condition = castChild;
            } else if (equivalent == getCqlEquivalent().getThen()) {
                then = castChild;
            } else if (equivalent == getCqlEquivalent().getElse()) {
                otherwise = castChild;
            } else {
                throw new UnsupportedChildNodeException(this, child);
            }
        } else {
            throw new UnsupportedChildNodeException(this, child);
        }
    }

    @Override
    public Segment toSegment() {
        var segment = new Segment();
        segment.setHead("IF(");
        segment.addChild(condition.toSegment());
        Segment commaSegment = new Segment();
        commaSegment.setHead(", ");
        segment.addChild(commaSegment);
        segment.addChild(then.toSegment());
        segment.addChild(commaSegment);
        segment.addChild(otherwise.toSegment());
        segment.setTail(")");
        return segment;
    }

    @Override
    public boolean isSimpleValue() {
        return true;
    }

    @Override
    public boolean isTable() {
        return then.isTable();
    }
}
