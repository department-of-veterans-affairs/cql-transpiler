package gov.va.transpiler.jinja.node;

import org.cqframework.cql.elm.tracking.Trackable;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.standards.Standards;
import gov.va.transpiler.jinja.state.State;

/**
 * Represents a node we don't support yet.
 */
public class DefaultNode extends CQLEquivalent<Trackable> {

    public DefaultNode(State state, Trackable t) {
        super(state, t);
    }

    @Override
    public Segment toSegment() {
        return toSegmentWithJoinedChildren(getChildren(), Standards.MACRO_FILE_NAME + "." + getCqlEquivalent().getClass().getSimpleName() + "(", ")", "", "", ", ");
    }
}
