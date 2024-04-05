package gov.va.transpiler.jinja.node.trackable.element.expression;

import java.util.List;
import java.util.Map;

import org.hl7.elm.r1.QueryLetRef;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.trackable.element.LetClauseNode;
import gov.va.transpiler.jinja.node.utilityinterfaces.ReferenceNode;
import gov.va.transpiler.jinja.state.State;

public class QueryLetRefNode extends ExpressionNode<QueryLetRef> implements ReferenceNode {
    // TODO: include reference source as nested operator tree inside output

    public QueryLetRefNode(State state, QueryLetRef cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public int allowedNumberOfChildren() {
        return 0;
    }

    @Override
    public String referenceType() {
        return LetClauseNode.REFERENCE_TYPE;
    }

    @Override
    public String referenceName() {
        return getCqlEquivalent().getName();
    }

    @Override
    protected Map<String, String> getLiteralArgumentMap() {
        var map = super.getLiteralArgumentMap();
        map.put("'name'", "'" + referenceName() + "'");
        return map;
    }

    @Override
    protected Map<String, List<TranspilerNode>> getNodeListArgumentMap() {
        var map = super.getNodeListArgumentMap();
        //map.put("'referenceValue'", Collections.singletonList((LetClauseNode) getReferenceTo()));
        return map;
    }
}
