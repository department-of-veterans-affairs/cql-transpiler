package gov.va.transpiler.jinja.node.trackable.element;

import java.util.Map;

import org.hl7.elm.r1.AliasedQuerySource;

import gov.va.transpiler.jinja.state.State;

public class AliasedQuerySourceNode<T extends AliasedQuerySource> extends ElementNode<T> {

    public AliasedQuerySourceNode(State state, T cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public int allowedNumberOfChildren() {
        return 1;
    }

    @Override
    protected Map<String, String> getLiteralArgumentMap() {
        var map = super.getLiteralArgumentMap();
        map.put("'alias'", "'" + getCqlEquivalent().getAlias() + "'");
        return map;
    }
}
