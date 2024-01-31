package gov.va.transpiler.jinja.node;

import java.util.ArrayList;
import java.util.List;

import org.cqframework.cql.elm.tracking.Trackable;

import gov.va.transpiler.jinja.node.unsupported.DisabledNode;
import gov.va.transpiler.jinja.node.utilityinterfaces.PrintsChildrenInList;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.standards.Standards;
import gov.va.transpiler.jinja.state.State;

/**
 * Represents a node we don't support yet.
 */
public class DefaultNode extends CQLEquivalent<Trackable> implements PrintsChildrenInList {
    private final List<TranspilerNode> children = new ArrayList<>();

    public DefaultNode(State state, Trackable t) {
        super(state, t);
    }

    @Override
    public void addChild(TranspilerNode child) throws UnsupportedChildNodeException {
        if (!(child instanceof DisabledNode)) {
            children.add(child);
        }
    }

    @Override
    public boolean isTable() {
        return false;
    }

    @Override
    public boolean isSimpleValue() {
        return true;
    }

    @Override
    public Segment toSegment() {
        return toSegmentWithJoinedChildren(children, Standards.MACRO_FILE_NAME + "." + getCqlEquivalent().getClass().getSimpleName() + "(", ")", "", "", ", ");
    }
}
