package gov.va.transpiler.jinja.node.trackable.element;

import java.util.ArrayList;
import java.util.List;

import org.hl7.elm.r1.Library;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.trackable.element.expressiondef.ExpressionDefNode;
import gov.va.transpiler.jinja.node.trackable.element.expressiondef.FunctionDefNode;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.printing.Segment.PrintType;
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
    }

    public String getAliasForLibrary(LibraryNode libraryNode) {
        var optional = includeDefNodeList.stream().filter(includeDefNode -> includeDefNode.getReferencedLibrary() == libraryNode).findFirst();
        if (optional.isPresent()) {
            return optional.get().getAliasForReferencedLibrary();
        } else {
            // Returned when something in this library is being referenced by something else within this library
            return null;
        }
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

        // Print the header
        var headerSegment = new Segment();
        headerSegment.setPrintType(PrintType.Line);
        // The provided macro file is used to convert the intermediate AST into the target language
        headerSegment.setHead( //
            "{%- import 'jinja_transpilation_libraries/sparksql/_operators_sparksql.j2' as _operators %}\n" + //
            "{%- import 'jinja_transpilation_libraries/_custom_functions_sparksql.j2' as _custom_functions %}\n" +//
            "{%- do _custom_functions.setup() %}\n" //
        );
        segment.addChild(headerSegment);

        // Include any other translated libraries required
        for (var child: includeDefNodeList) {
            segment.addChild(child.toSegment());
        }

        // Print children
        for (var child: getChildren()) {
            // child nodes are set up as ASTs equivalent to CQL statements
            segment.addChild(child.toSegment());

            // invoke the child node so that when the library file is compiled with jinja, each child is printed as SQL
            if (child instanceof ExpressionDefNode && !(child instanceof FunctionDefNode)) {
                segment.addChild(new Segment("{{ " + ((ExpressionDefNode<?>) child).referenceName() + "(none) }}\n"));
            }
        }
        return segment;
    }
}
