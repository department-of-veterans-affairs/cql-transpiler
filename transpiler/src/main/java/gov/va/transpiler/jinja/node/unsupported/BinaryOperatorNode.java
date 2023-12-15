package gov.va.transpiler.jinja.node.unsupported;

import org.cqframework.cql.elm.tracking.Trackable;

import gov.va.transpiler.jinja.state.State;

public class BinaryOperatorNode extends UnsupportedNode {

    public BinaryOperatorNode(State state, Trackable t) {
        super(state, t);
    }    
}
