package gov.va.transpiler.jinja.node.trackable.element;

import org.hl7.elm.r1.SortClause;

import gov.va.transpiler.jinja.state.State;

public class SortClauseNode extends ElementNode<SortClause> {

    public SortClauseNode(State state, SortClause cqlEquivalent) {
        super(state, cqlEquivalent);
    }
}
