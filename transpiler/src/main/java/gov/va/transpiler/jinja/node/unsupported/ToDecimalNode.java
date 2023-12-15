package gov.va.transpiler.jinja.node.unsupported;

import org.cqframework.cql.elm.tracking.Trackable;

import gov.va.transpiler.jinja.state.State;

public class ToDecimalNode extends UnsupportedNode {

    public ToDecimalNode(State state, Trackable t) {
        super(state, t);
    }
}
