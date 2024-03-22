package gov.va.transpiler.jinja.node;

import org.cqframework.cql.elm.tracking.Trackable;

import gov.va.transpiler.jinja.standards.Standards;
import gov.va.transpiler.jinja.state.State;

public class DisabledNode extends CQLEquivalent<Trackable> {

    public DisabledNode(State state, Trackable cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public String getOperator() {
        return Standards.macroFileName() + "." + Standards.DISABLED_OPERATOR;
    }
}
