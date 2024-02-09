package gov.va.transpiler.jinja.node.trackable.element;

import org.hl7.elm.r1.AliasedQuerySource;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.printing.Segment.PrintType;
import gov.va.transpiler.jinja.state.State;

public class AliasedQuerySourceNode extends ElementNode<AliasedQuerySource> {

    public AliasedQuerySourceNode(State state, AliasedQuerySource cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public Segment toSegment() {
        var segment = new Segment(getName() + "(", ")", PrintType.Inline);
        segment.addChild(new Segment("'" + getCqlEquivalent().getAlias() + "', "));
        segment.addChild(getChild().toSegment());
        return segment;
    }
}
