package gov.va.transpiler.jinja.node.unsupported;

import org.cqframework.cql.elm.tracking.Trackable;

import gov.va.transpiler.jinja.state.State;

public class ByExpressionNode extends UnsupportedNode {

    public ByExpressionNode(State state, Trackable t) {
        super(state, t);
    }
}