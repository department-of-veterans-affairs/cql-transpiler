package gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression;

import java.util.Map;

import org.hl7.elm.r1.AliasRef;

import gov.va.transpiler.jinja.node.trackable.element.expression.ExpressionNode;
import gov.va.transpiler.jinja.node.trackable.element.expression.QueryNode;
import gov.va.transpiler.jinja.state.State;

public class AliasRefNode extends ExpressionNode<AliasRef> {

    public AliasRefNode(State state, AliasRef cqlEquivalent) {
        super(state, cqlEquivalent);
        ((QueryNode) getParentOfClass(QueryNode.class)).addAliasRefNode(this);
    }

    @Override
    protected Map<String, String> getLiteralArgumentMap() {
        var map = super.getLiteralArgumentMap();
        map.put("'referencedName'", "'" + getCqlEquivalent().getName() + "'");
        return map;
    }

    @Override
    public int allowedNumberOfChildren() {
        return 0;
    }
}
