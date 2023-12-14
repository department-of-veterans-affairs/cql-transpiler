package gov.va.transpiler.jinja.node.leaf;

import org.hl7.elm.r1.UsingDef;

import gov.va.transpiler.jinja.converter.State;
import gov.va.transpiler.jinja.printing.Segment;

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

    @Override
    public PrintType getPrintType() {
        return PrintType.Inline;
    }
}
