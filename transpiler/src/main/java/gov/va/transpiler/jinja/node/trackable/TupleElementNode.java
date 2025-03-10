package gov.va.transpiler.jinja.node.trackable;

import java.util.Map;

import org.hl7.elm.r1.TupleElement;

import gov.va.transpiler.jinja.state.State;

public class TupleElementNode extends TrackableNode<TupleElement> {

    public TupleElementNode(State state, TupleElement cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public int allowedNumberOfChildren() {
        return 1;
    }

    @Override
    protected Map<String, String> getLiteralArgumentMap() {
        var map = super.getLiteralArgumentMap();
        map.put("'name'", "'" + getCqlEquivalent().getName() + "'");
        return map;
    }
}
