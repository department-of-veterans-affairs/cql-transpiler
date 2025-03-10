package gov.va.transpiler.jinja.node.trackable.element;

import org.hl7.elm.r1.ReturnClause;

import gov.va.transpiler.jinja.state.State;

public class ReturnClauseNode extends ElementNode<ReturnClause> {

    public ReturnClauseNode(State state, ReturnClause cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public int allowedNumberOfChildren() {
        return 1;
    }
}
