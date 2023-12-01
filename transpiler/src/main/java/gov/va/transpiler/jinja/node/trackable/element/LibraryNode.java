package gov.va.transpiler.jinja.node.trackable.element;

import java.util.ArrayList;
import java.util.List;

import org.hl7.elm.r1.Library;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.utilityinterfaces.DirectPrint;
import gov.va.transpiler.jinja.node.utilityinterfaces.ReferenceableNode;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.printing.Segment.PrintType;
import gov.va.transpiler.jinja.standards.Standards;
import gov.va.transpiler.jinja.state.State;

public class LibraryNode extends ElementNode<Library> implements ReferenceableNode {

    private List<UsingDefNode> usingDefNodeList = new ArrayList<>();
    private List<IncludeDefNode> includeDefNodeList = new ArrayList<>();
    private List<ContextDefNode> contextDefNodeList = new ArrayList<>();
    private List<ParameterDefNode> parameterDefNodeList = new ArrayList<>();

    public LibraryNode(State state, Library t) {
        super(state, t);
        state.setCurrentLibrary(getCqlEquivalent().getIdentifier().getId(), getCqlEquivalent().getIdentifier().getVersion(), this);
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

    @Override
    public String getTargetFileLocation() {
        return getCqlEquivalent().getIdentifier().getId() == null ? "Anonymous Library" : getCqlEquivalent().getIdentifier().getVersion() == null ? sanitizeNameForJinja(referenceName()) : sanitizeNameForJinja(referenceName() + "_" + getCqlEquivalent().getIdentifier().getVersion());
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
                var subHeaderSegment = new Segment("{%- from '" + Standards.GENERATED_INTERMEDIATE_AST_FOLDER + Standards.FOLDER_SEPARATOR + macroSetEntry.getKey() + Standards.JINJA_FILE_POSTFIX + "' import " + macro + " %}");
                subHeaderSegment.setPrintType(PrintType.Line);
                headerSegment.addChild(subHeaderSegment);
            }
        }

        // Print children
        for (var child: getChildren()) {
            // child nodes are set up as ASTs equivalent to CQL statements
            if (child instanceof DirectPrint) {
                segment.addChild(((DirectPrint)child).toSegmentWrapped());
            } else {
                segment.addChild(child.toSegment());
            }
        }
        return segment;
    }

    @Override
    public String referenceName() {
        return getCqlEquivalent().getIdentifier().getId();
    }
}
