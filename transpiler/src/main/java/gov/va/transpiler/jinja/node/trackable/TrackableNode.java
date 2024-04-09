package gov.va.transpiler.jinja.node.trackable;

import org.cqframework.cql.elm.tracking.Trackable;

import gov.va.transpiler.jinja.node.CQLEquivalent;
import gov.va.transpiler.jinja.state.State;

public class TrackableNode<T extends Trackable> extends CQLEquivalent<T> {

    public TrackableNode(State state, T cqlEquivalent) {
        super(state, cqlEquivalent);
    }
}
