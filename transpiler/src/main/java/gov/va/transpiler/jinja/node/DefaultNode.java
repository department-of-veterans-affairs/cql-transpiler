package gov.va.transpiler.jinja.node;

import java.util.Map;

import org.cqframework.cql.elm.tracking.Trackable;

import gov.va.transpiler.jinja.standards.Standards;
import gov.va.transpiler.jinja.state.State;

/**
 * Represents a node we don't support yet.
 */
public class DefaultNode extends CQLEquivalent<Trackable> {

    public DefaultNode(State state, Trackable t) {
        super(state, t);
    }

    @Override
    public String getOperator() {
        return Standards.macroFileName() + "." + Standards.DEFAULT_OPERATOR;
    }

    @Override
    protected Map<String, String> getSimpleArgumentMap() {
        var map = super.getSimpleArgumentMap();
        map.put("'unsupportedOperator'", "'" + super.getOperator() + "'");
        return map;
    }
}
