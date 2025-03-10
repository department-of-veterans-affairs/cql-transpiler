package gov.va.transpiler.jinja.node;

import java.util.Map;

import org.cqframework.cql.elm.tracking.Trackable;

import gov.va.transpiler.jinja.standards.Standards;
import gov.va.transpiler.jinja.state.State;

/**
 * Represents a node we don't support yet.
 */
public class UnsupportedNode extends CQLEquivalent<Trackable> {

    public UnsupportedNode(State state, Trackable t) {
        super(state, t);
    }

    @Override
    public String getOperator() {
        return Standards.UNSUPPORTED_OPERATOR;
    }

    @Override
    protected Map<String, String> getLiteralArgumentMap() {
        var map = super.getLiteralArgumentMap();
        map.put("'unsupportedOperator'", "'" + Standards.ENVIRONMENT_NAME + "." + super.getOperator() + "'");
        return map;
    }
}
