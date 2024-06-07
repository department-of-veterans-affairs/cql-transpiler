package gov.va.transpiler.jinja.node.trackable.element;

import java.util.ArrayList;
import java.util.List;

import org.hl7.elm.r1.Library;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.printing.Segment.PrintType;
import gov.va.transpiler.jinja.standards.Standards;
import gov.va.transpiler.jinja.state.State;

public class LibraryNode extends ElementNode<Library> {

    private List<UsingDefNode> usingDefNodeList = new ArrayList<>();
    private List<IncludeDefNode> includeDefNodeList = new ArrayList<>();
    private List<ContextDefNode> contextDefNodeList = new ArrayList<>();
    private List<ParameterDefNode> parameterDefNodeList = new ArrayList<>();

    public LibraryNode(State state, Library t) {
        super(state, t);
        state.setCurrentLibraryAndAddToLibraryMap(this);
    }

    @Override
    public void addChild(TranspilerNode child) {
        if (child instanceof UsingDefNode) {
            usingDefNodeList.add((UsingDefNode) child);
        } else if (child instanceof IncludeDefNode) {
            includeDefNodeList.add((IncludeDefNode) child);
        } else if (child instanceof ContextDefNode) {
            contextDefNodeList.add((ContextDefNode) child);
        } else if (child instanceof ParameterDefNode) {
            parameterDefNodeList.add((ParameterDefNode) child);
        } else {
            super.addChild(child);
        }
        processChildDependencies(child);
    }

    public boolean isReferenceToExternalLibrary(LibraryNode libraryNode) {
        var optional = includeDefNodeList.stream().filter(includeDefNode -> includeDefNode.getReferencedLibrary() == libraryNode).findFirst();
        return optional.isPresent();
    }

    @Override
    public String getTargetFileLocation() {
        return getCqlEquivalent().getIdentifier().getId() == null ? "Anonymous Library" : getCqlEquivalent().getIdentifier().getVersion() == null ? getCqlEquivalent().getIdentifier().getId() : getCqlEquivalent().getIdentifier().getId() + "_" + getCqlEquivalent().getIdentifier().getVersion();
    }

    @Override
    public Segment toSegment() {
        // Encapsulate children in a file
        var segment = new Segment();
        segment.setPrintType(PrintType.File);
        segment.setOriginalLibraryIdentifier(getCqlEquivalent().getIdentifier());
        segment.setFileLocation(getTargetFileLocation());

        // Print the headers
        var headerSegment = new Segment();
        segment.addChild(headerSegment);

        // Import every other macro used inside this file
        for (var macroSetEntry : getMacroDependencies().entrySet()) {
            for (var macro : macroSetEntry.getValue()) {
                var subHeaderSegment = new Segment("{%- from '" + macroSetEntry.getKey() + Standards.JINJA_FILE_POSTFIX + "' import " + macro + " %}");
                subHeaderSegment.setPrintType(PrintType.Line);
                headerSegment.addChild(subHeaderSegment);
            }
        }

        // Print children
        for (var child: getChildren()) {
            // child nodes are set up as ASTs equivalent to CQL statements
            segment.addChild(child.toSegment());
        }
        return segment;
    }
}
