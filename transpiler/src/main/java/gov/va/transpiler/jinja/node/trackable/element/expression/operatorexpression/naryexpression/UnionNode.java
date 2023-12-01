package gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression.naryexpression;

import java.util.Map;

import org.hl7.elm.r1.As;
import org.hl7.elm.r1.Union;

import gov.va.transpiler.jinja.node.CQLEquivalent;
import gov.va.transpiler.jinja.state.State;

public class UnionNode extends NaryExpressionNode<Union> {

    public UnionNode(State state, Union cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    protected Map<String, String> getLiteralArgumentMap() {
        var map = super.getLiteralArgumentMap();
        // If children need to be cast, it's because they're of varying types.
        var isMixed = getChildren().stream().allMatch(child -> child instanceof CQLEquivalent && ((CQLEquivalent<?>) child).getCqlEquivalent() instanceof As);
        map.put("'mixed'", isMixed ? "'true'" : "'false'");
        return map;
    }
}
