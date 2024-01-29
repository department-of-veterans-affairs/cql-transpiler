package gov.va.transpiler.sparkjinja.node.leaf;

import org.hl7.elm.r1.UsingDef;

import gov.va.transpiler.sparkjinja.printing.Segment;
import gov.va.transpiler.sparkjinja.state.State;

public class UsingDefNode extends Leaf<UsingDef> {

    public UsingDefNode(State state, UsingDef t) {
        super(state, t);
    }

    @Override
    public boolean isTable() {
        return false;
    }

    @Override
    public boolean isSimpleValue() {
        return false;
    }

    @Override
    public Segment toSegment() {
        throw new UnsupportedOperationException();
    }
}
