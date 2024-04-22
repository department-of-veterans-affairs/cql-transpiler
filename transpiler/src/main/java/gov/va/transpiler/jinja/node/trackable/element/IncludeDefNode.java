package gov.va.transpiler.jinja.node.trackable.element;

import org.hl7.elm.r1.IncludeDef;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.printing.Segment.PrintType;
import gov.va.transpiler.jinja.standards.Standards;
import gov.va.transpiler.jinja.state.State;

public class IncludeDefNode extends ElementNode<IncludeDef> {

    private LibraryNode referencedLibrary;

    public IncludeDefNode(State state, IncludeDef cqlEquivalent) {
        super(state, cqlEquivalent);
        referencedLibrary = state.getLibraryNodeByIDAndVersion(getCqlEquivalent().getPath(), getCqlEquivalent().getVersion());
    }

    /**
     * @return Library this include statement references.
     */
    public LibraryNode getReferencedLibrary() {
        return referencedLibrary;
    }

    /**
     * @return Alias to use inside a macro file. Substitutes whitespace and restricted characters permissible in CQL variable names but impermissible in Jinja.
     */
    public String getAliasForReferencedLibrary() {
        return getCqlEquivalent().getLocalIdentifier() == null ? getReferencedLibrary().getTargetFileLocation()
            .replace(" ", "_")
            .replace(".", "__") : getCqlEquivalent().getLocalIdentifier();
    }

    @Override
    public Segment toSegment() {
        return new Segment("{% import '" + referencedLibrary.getTargetFileLocation() + Standards.JINJA_FILE_POSTFIX + "' as " + getAliasForReferencedLibrary(), " %}", PrintType.Line);
    }
}
