package gov.va.transpiler.jinja.node.ary;

import java.util.ArrayList;
import java.util.List;

import org.hl7.elm.r1.Library;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.leaf.UsingDefNode;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class LibraryNode extends Ary<Library> {

    private List<UsingDefNode> usingDefNodeList = new ArrayList<>();

    public LibraryNode(State state, Library t) {
        super(state, t);
    }

    @Override
    public void addChild(TranspilerNode child) {
        if (child instanceof UsingDefNode) {
            usingDefNodeList.add((UsingDefNode) child);
        } else {
            super.addChild(child);
        }
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
    public PrintType getPrintType() {
        return PrintType.Folder;
    }

    @Override
    public String referenceIs() {
        var identifier = getCqlEquivalent().getIdentifier();
        return identifier.getId() == null ? "Anonymous Library" : identifier.getVersion() == null ? identifier.getId() : identifier.getId() + "_" + identifier.getVersion();
    }

    @Override
    public Segment toSegment() {
        var segment = new Segment(this);
        getChildren().stream().forEach(child -> segment.addSegmentToBody(child.toSegment()));
        return segment;
    }
}
