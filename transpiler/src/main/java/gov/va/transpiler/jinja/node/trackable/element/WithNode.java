package gov.va.transpiler.jinja.node.trackable.element;

import org.hl7.elm.r1.With;

import gov.va.transpiler.jinja.state.State;

public class WithNode extends RelationshipClauseNode<With> {

    public WithNode(State state, With cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public int allowedNumberOfChildren() {
        return 1;
    }
}
