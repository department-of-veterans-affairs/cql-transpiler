package gov.va.transpiler.jinja.node.trackable.element;

import java.util.Map;

import org.hl7.elm.r1.AliasedQuerySource;

import gov.va.transpiler.jinja.state.State;

public class AliasedQuerySourceNode extends ElementNode<AliasedQuerySource> {

    public AliasedQuerySourceNode(State state, AliasedQuerySource cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    protected Map<String, String> getSimpleArgumentMap() {
        var map = super.getSimpleArgumentMap();
        map.put("'alias'", "'" + getCqlEquivalent().getAlias() + "'");
        return map;
    }
}
