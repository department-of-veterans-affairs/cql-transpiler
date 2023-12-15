package gov.va.transpiler.jinja.node.unsupported;

import org.cqframework.cql.elm.tracking.Trackable;

import gov.va.transpiler.jinja.state.State;

public class ConcatenateNode extends UnsupportedNode {

    public ConcatenateNode(State state, Trackable t) {
        super(state, t);
    }
}