package gov.va.transpiler.sparkjinja.node.unsupported;

import org.cqframework.cql.elm.tracking.Trackable;

import gov.va.transpiler.sparkjinja.state.State;

public class NotNode extends UnsupportedNode {

    public NotNode(State state, Trackable t) {
        super(state, t);
    }
}
