package gov.va.transpiler.jinja.node.unsupported;

import org.cqframework.cql.elm.tracking.Trackable;

import gov.va.transpiler.jinja.state.State;

public class ToDateNode extends UnsupportedNode {

    public ToDateNode(State state, Trackable t) {
        super(state, t);
    }
}
