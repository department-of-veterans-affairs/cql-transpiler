package gov.va.transpiler.jinja.node.trackable.element;

import java.util.Map;

import org.hl7.elm.r1.LetClause;

import gov.va.transpiler.jinja.node.trackable.element.expression.QueryNode;
import gov.va.transpiler.jinja.node.utilityinterfaces.ReferenceableNode;
import gov.va.transpiler.jinja.state.State;

public class LetClauseNode extends ElementNode<LetClause> implements ReferenceableNode {

    public LetClauseNode(State state, LetClause cqlEquivalent) {
        super(state, cqlEquivalent);
        ((QueryNode) getParentOfClass(QueryNode.class)).addNamedChild(referenceName(), this);
    }

    @Override
    public int allowedNumberOfChildren() {
        return 1;
    }

    @Override
    protected Map<String, String> getLiteralArgumentMap() {
        var map = super.getLiteralArgumentMap();
        map.put("'referenceName'", "'" + referenceName() + "'");
        return map;
    }

    @Override
    public String referenceName() {
        return getCqlEquivalent().getIdentifier();
    }
}
