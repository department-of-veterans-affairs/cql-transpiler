package gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression;

import java.util.Map;

import org.hl7.elm.r1.AliasRef;

import gov.va.transpiler.jinja.node.trackable.element.expression.ExpressionNode;
import gov.va.transpiler.jinja.state.State;

public class AliasRefNode extends ExpressionNode<AliasRef> {
    // TODO: include source (AlaisedQuerySource) as nested operator tree inside output

    public AliasRefNode(State state, AliasRef cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    protected Map<String, String> getSimpleArgumentMap() {
        var map = super.getSimpleArgumentMap();
        map.put("'name'", "'" + getCqlEquivalent().getName() + "'");
        return map;
    }

    @Override
    public int allowedNumberOfChildren() {
        return 0;
    }
}
