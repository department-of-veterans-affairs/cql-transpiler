package gov.va.transpiler.jinja.node.trackable.element.expression;

import java.util.Map;

import org.hl7.elm.r1.QueryLetRef;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.trackable.element.LetClauseNode;
import gov.va.transpiler.jinja.node.utilityinterfaces.ReferenceNode;
import gov.va.transpiler.jinja.state.State;

public class QueryLetRefNode extends ExpressionNode<QueryLetRef> implements ReferenceNode<LetClauseNode> {

    public QueryLetRefNode(State state, QueryLetRef cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public int allowedNumberOfChildren() {
        return 0;
    }

    @Override
    protected Map<String, String> getLiteralArgumentMap() {
        var map = super.getLiteralArgumentMap();
        map.put("'referencedName'", "'" + referencedName() + "'");
        return map;
    }

    @Override
    protected Map<String, TranspilerNode> getNodeArgumentMap() {
        var map = super.getNodeArgumentMap();
        map.put("'referenceTo'", referenceTo());
        return map;
    }

    @Override
    public String referencedName() {
        return getCqlEquivalent().getName();
    }

    @Override
    public LetClauseNode referenceTo() {
        return (LetClauseNode) getNamedChildOfClassFromParentOfOtherClass(referencedName(), LetClauseNode.class, QueryNode.class);
    }
}
