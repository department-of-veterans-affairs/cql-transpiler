package gov.va.transpiler.jinja.node.trackable.element;

import java.util.Map;

import org.hl7.elm.r1.IncludeDef;

import gov.va.transpiler.jinja.node.utilityinterfaces.DirectPrint;
import gov.va.transpiler.jinja.node.utilityinterfaces.ReferenceNode;
import gov.va.transpiler.jinja.node.utilityinterfaces.ReferenceableNode;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.printing.Segment.PrintType;
import gov.va.transpiler.jinja.standards.Standards;
import gov.va.transpiler.jinja.state.State;

public class IncludeDefNode extends ElementNode<IncludeDef> implements ReferenceableNode, ReferenceNode<LibraryNode>, DirectPrint {

    private final LibraryNode enclosingLibrary;
    private final LibraryNode referencedLibrary;

    public IncludeDefNode(State state, IncludeDef cqlEquivalent) {
        super(state, cqlEquivalent);
        enclosingLibrary = state.getCurrentLibrary();
        enclosingLibrary.addNamedChild(referenceName(), this);
        referencedLibrary = state.getLibraryByIdAndVersion(referencedName(), getCqlEquivalent().getVersion());
    }

    @Override
    public Segment toSegmentWrapped() {
        return new Segment("{% import '" + referencedLibrary.getTargetFileLocation() + Standards.JINJA_FILE_POSTFIX + "' as " + sanitizeNameForJinja(referenceName()), " %}", PrintType.Line);
    }

    @Override
    protected Map<String, String> getLiteralArgumentMap() {
        var map = super.getLiteralArgumentMap();
        map.put("'include'", "'" + referencedLibrary.getTargetFileLocation() + "'");
        map.put("'referenceName'", "'" + referenceName() + "'");
        return map;
    }

    @Override
    public String referenceName() {
        return getCqlEquivalent().getLocalIdentifier();
    }

    @Override
    public String referencedName() {
        return getCqlEquivalent().getPath();
    }

    @Override
    public LibraryNode referenceTo() {
        return referencedLibrary;
    }
}
