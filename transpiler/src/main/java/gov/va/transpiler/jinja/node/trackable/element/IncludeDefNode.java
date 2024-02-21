package gov.va.transpiler.jinja.node.trackable.element;

import org.hl7.elm.r1.IncludeDef;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.printing.Segment.PrintType;
import gov.va.transpiler.jinja.state.State;

public class IncludeDefNode extends ElementNode<IncludeDef> {

    private LibraryNode referencedLibrary;
    public IncludeDefNode(State state, IncludeDef cqlEquivalent) {
        super(state, cqlEquivalent);
        referencedLibrary = state.getLibraryNodeByIDAndVersion(getCqlEquivalent().getPath(), getCqlEquivalent().getVersion());
    }

    public LibraryNode getReferencedLibrary() {
        return referencedLibrary;
    }

    public String getAliasForReferencedLibrary() {
        return getCqlEquivalent().getLocalIdentifier() == null ? getReferencedLibrary().getTargetFileLocation()
            .replace(" ", "_")
            .replace(".", "__") : getCqlEquivalent().getLocalIdentifier();
    }

    @Override
    public Segment toSegment() {
        return new Segment("{% import '" + referencedLibrary.getTargetFileLocation() + "' as " + getAliasForReferencedLibrary(), " %}", PrintType.Line);
    }
}
