package gov.va.transpiler.jinja.node.unary;

import org.hl7.elm.r1.AliasedQuerySource;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class AliasedQuerySourceNode extends Unary<AliasedQuerySource> {

    public AliasedQuerySourceNode(State state, AliasedQuerySource t) {
        super(state, t);
    }

    @Override
    public Segment toSegment() {
        var segment = new Segment();
        if (getChild().isSimpleValue()) {
            segment.setHead("SELECT * FROM (");
            segment.addChild(getChild().toSegment());
            segment.setTail(") AS " + getCqlEquivalent().getAlias());
        } else {
            segment.addChild(getChild().toSegment());
            segment.setTail(" AS " + getCqlEquivalent().getAlias());
        }
        return segment;
    }    
}
