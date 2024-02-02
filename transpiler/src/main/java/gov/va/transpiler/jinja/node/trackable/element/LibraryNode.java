package gov.va.transpiler.jinja.node.trackable.element;

import java.util.ArrayList;
import java.util.List;

import org.hl7.elm.r1.Library;

import gov.va.transpiler.jinja.node.CQLEquivalent;
import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.printing.Segment.PrintType;
import gov.va.transpiler.jinja.standards.Standards;
import gov.va.transpiler.jinja.state.State;

public class LibraryNode extends CQLEquivalent<Library> {

    private List<UsingDefNode> usingDefNodeList = new ArrayList<>();
    private List<ValueSetDefNode> valueSetDefNodeList = new ArrayList<>();

    public LibraryNode(State state, Library t) {
        super(state, t);
    }

    @Override
    public void addChild(TranspilerNode child) {
        if (child instanceof UsingDefNode) {
            usingDefNodeList.add((UsingDefNode) child);
        } else if (child instanceof ValueSetDefNode) {
            valueSetDefNodeList.add((ValueSetDefNode) child);
        } else {
            super.addChild(child);
        }
    }

    @Override
    public String getTargetFileLocation() {
        var identifier = getCqlEquivalent().getIdentifier();
        return identifier.getId() == null ? "Anonymous Library" : identifier.getVersion() == null ? identifier.getId() : identifier.getId() + "_" + identifier.getVersion();
    }

    @Override
    public Segment toSegment() {
        var segment = new Segment();
        segment.setPrintType(PrintType.File);
        segment.setOriginalLibraryIdentifier(getCqlEquivalent().getIdentifier());
        segment.setFileLocation(getTargetFileLocation());
        var headerSegment = new Segment();
        headerSegment.setPrintType(PrintType.Line);
        headerSegment.setHead("{% import '" + Standards.MACRO_FILE_NAME + "' as " + Standards.MACRO_FILE_NAME +" %}");
        segment.addChild(headerSegment);
        for (var child: getChildren()) {
            segment.addChild(childToSegment(child));
        }
        return segment;
    }
}
