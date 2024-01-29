package gov.va.transpiler.sparkjinja.node.unsupported;

import org.cqframework.cql.elm.tracking.Trackable;

import gov.va.transpiler.sparkjinja.state.State;

public class ParameterRefNode extends UnsupportedNode {

    public ParameterRefNode(State state, Trackable t) {
        super(state, t);
    }
}
