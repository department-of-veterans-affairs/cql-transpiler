package gov.va.transpiler.jinja.node.leaf;

import org.hl7.elm.r1.ExpressionDef;
import org.hl7.elm.r1.ExpressionRef;

import gov.va.transpiler.jinja.node.ReferenceNode;
import gov.va.transpiler.jinja.node.unary.ExpressionDefNode;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class ExpressionRefNode extends Leaf<ExpressionRef> implements ReferenceNode {

    public ExpressionRefNode(State state, ExpressionRef t) {
        super(state, t);
        if (getReferenceTo() instanceof ExpressionDef) {

        }
    }

    @Override
    public Segment toSegment() {
        var segment = new Segment();
        segment.setHead("{{ ref(\"" + ((ExpressionDefNode) getReferenceTo()).getTargetFileLocation() + "\") }}");
        return segment;
    }

    @Override
    public boolean isTable() {
        return ((ExpressionDefNode) getReferenceTo()).isTable();
    }

    @Override
    public boolean isSimpleValue() {
        return ((ExpressionDefNode) getReferenceTo()).isSimpleValue();
    }

    @Override
    public String referenceType() {
        return ExpressionDefNode.REFERENCE_TYPE;
    }

    @Override
    public String referenceName() {
        return getCqlEquivalent().getName();
    }
}
