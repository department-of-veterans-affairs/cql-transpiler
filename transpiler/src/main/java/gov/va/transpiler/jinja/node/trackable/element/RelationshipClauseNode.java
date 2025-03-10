package gov.va.transpiler.jinja.node.trackable.element;

import java.util.Map;

import org.hl7.elm.r1.RelationshipClause;

import gov.va.transpiler.jinja.node.CQLEquivalent;
import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.state.State;

public class RelationshipClauseNode<T extends RelationshipClause> extends AliasedQuerySourceNode<T> {

    private TranspilerNode suchThatNode;

    public RelationshipClauseNode(State state, T cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public void addChild(TranspilerNode child) {
        if (child instanceof CQLEquivalent && ((CQLEquivalent<?>) child).getCqlEquivalent() == getCqlEquivalent().getSuchThat()) {
            suchThatNode = child;
        } else {
            super.addChild(child);
        }
        processChildDependencies(child);
    }

    @Override
    protected Map<String, TranspilerNode> getNodeArgumentMap() {
        var map = super.getNodeArgumentMap();
        map.put("'suchThat'", suchThatNode);
        return map;
    }
}
