package gov.va.transpiler.jinja.node.trackable.element;

import org.hl7.elm.r1.LetClause;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.utilityinterfaces.ReferenceableNode;
import gov.va.transpiler.jinja.state.State;

public class LetClauseNode extends ElementNode<LetClause> implements ReferenceableNode {

    public static final String REFERENCE_TYPE = "LetClause";

    public LetClauseNode(State state, LetClause cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public String referenceType() {
        return REFERENCE_TYPE;
    }

    @Override
    public String referenceName() {
        return getCqlEquivalent().getIdentifier().replace(' ', '_');
    }

    @Override
    public TranspilerNode getChildByReference(String nameOrIndex) {
        return getChild().getChildByReference(nameOrIndex);
    }
}
