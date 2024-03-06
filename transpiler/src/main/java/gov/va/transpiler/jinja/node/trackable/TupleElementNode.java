package gov.va.transpiler.jinja.node.trackable;

import java.util.Map;

import org.hl7.elm.r1.TupleElement;

import gov.va.transpiler.jinja.state.State;

public class TupleElementNode extends TrackableNode<TupleElement> {

    private String context;

    public TupleElementNode(State state, TupleElement cqlEquivalent) {
        super(state, cqlEquivalent);
        context = state.getContext();
    }

    @Override
    public int allowedNumberOfChildren() {
        return 1;
    }

    @Override
    protected Map<String, String> getStringArgumentMap() {
        var map = super.getStringArgumentMap();
        map.put("context", context);
        return map;
    }
}
