package gov.va.transpiler.jinja.node.ary;

import org.hl7.elm.r1.FunctionRef;

import gov.va.transpiler.jinja.node.ReferenceNode;
import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.unary.FunctionDefNode;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class FunctionRefNode extends Ary<FunctionRef> implements ReferenceNode {

    public FunctionRefNode(State state, FunctionRef t) {
        super(state, t);
    }

    @Override
    public String referenceType() {
        return FunctionDefNode.REFERENCE_TYPE;
    }

    @Override
    public String referenceName() {
        return getCqlEquivalent().getName();
    }

    @Override
    public boolean isTable() {
        return ((FunctionDefNode) getReferenceTo()).isTable();
    }

    @Override
    public boolean isSimpleValue() {
        return ((FunctionDefNode) getReferenceTo()).isSimpleValue();
    }

    @Override
    protected Segment childToSegment(TranspilerNode child) {
        return containerizer.childToSegmentContainerizing(child);
    }

    @Override
    public Segment toSegment() {
        return toSegmentWithJoinedChildren("{{ " + ((FunctionDefNode) getReferenceTo()).getTargetFileLocation() + "(", ") }}", "(", ")", ", ", ",");
    }
}
