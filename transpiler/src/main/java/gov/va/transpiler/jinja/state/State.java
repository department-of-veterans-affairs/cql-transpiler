package gov.va.transpiler.jinja.state;

import java.util.LinkedHashMap;
import java.util.Map;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.trackable.element.LibraryNode;
import gov.va.transpiler.jinja.node.utilityinterfaces.ReferenceNode;
import gov.va.transpiler.jinja.node.utilityinterfaces.ReferenceableNode;
import gov.va.transpiler.jinja.standards.Standards;

public class State {

    private TranspilerNode currentNode = null;
    private String context = Standards.UNFILTERED_CONTEXT;
    private Map<String, Map<String, ReferenceableNode>> typeAndNametoReferenceMap = new LinkedHashMap<>();
    private LibraryNode currentLibraryNode = null;
    private Map<ReferenceableNode, LibraryNode> referenceToLibraryMap = new LinkedHashMap<>();
    private Map<String, Map<String, LibraryNode>> idAndVersionToLibraryMap = new LinkedHashMap<>();

    /**
     * @return The current node.
     */
    public TranspilerNode getCurrentNode() {
        return currentNode;
    }

    /**
     * @param currentNode Sets the current node.
     */
    public void setCurrentNode(TranspilerNode currentNode) {
        this.currentNode = currentNode;
    }

    /**
     * @return If the current CQL context is null, returns {@link Standards#UNFILTERED_CONTEXT}. Otherwise returns the current CQL context.
     */
    public String getContext() {
        return context == null ? Standards.UNFILTERED_CONTEXT : context;
    }


    /**
     * @return Sets the current CQL context.
     */
    public void setContext(String context) {
        this.context = context;
    }

    /**
     * Stores a {@link ReferenceableNode} to be retrieved later. {@link ReferenceableNode}s are keyed by their type and name, and associated to the current library.
     * 
     * @param referenceableNode Reference node to store.
     */
    public void addReference(ReferenceableNode referenceableNode) {
        var typeReferenceMap = typeAndNametoReferenceMap.getOrDefault(referenceableNode.referenceType(), new LinkedHashMap<>());
        typeReferenceMap.put(referenceableNode.referenceName(), referenceableNode);
        referenceToLibraryMap.put(referenceableNode, currentLibraryNode);
        typeAndNametoReferenceMap.putIfAbsent(referenceableNode.referenceType(), typeReferenceMap);
    }

    /**
     * @param referenceNode Reference to a {@link ReferenceableNode}
     * @return {@link ReferenceableNode} referenced.
     */
    public ReferenceableNode getReference(ReferenceNode referenceNode) {
        return typeAndNametoReferenceMap.get(referenceNode.referenceType()).get(referenceNode.referenceName());
    }

    /**
     * @return The {@link LibraryNode} corresponding the {@link org.hl7.elm.r1.Library} whose children are currently being transversed.
     */
    public LibraryNode getCurrentLibraryNode() {
        return currentLibraryNode;
    }

    /**
     * Takes the provided {@link LibraryNode} and sets it as the current libray. Also inserts it into a map of libraries keyed by its ID.
     * 
     * @param currentLibraryNode Library to set.
     */
    public void setCurrentLibraryAndAddToLibraryMap(LibraryNode currentLibraryNode) {
        this.currentLibraryNode = currentLibraryNode;
        addLibraryNode(getCurrentLibraryNode().getCqlEquivalent().getIdentifier().getId(), getCurrentLibraryNode().getCqlEquivalent().getIdentifier().getVersion(), getCurrentLibraryNode());
    }

    /**
     * Helper function to store a library into a map, keyed by the library's id and version.
     * 
     * @param id Library ID.
     * @param version Library version.
     * @param libraryNode Library.
     */
    protected void addLibraryNode(String id, String version, LibraryNode libraryNode) {
        var versionMap = idAndVersionToLibraryMap.getOrDefault(version, new LinkedHashMap<>());
        versionMap.put(version, libraryNode);
        idAndVersionToLibraryMap.putIfAbsent(id, versionMap);
    }

    /**
     * @param referenceNode {@link ReferenceableNode} to find associated library for.
     * @return {@link LibraryNode} a {@link ReferenceableNode} is associated with.
     */
    public LibraryNode getLibraryNodeForReference(ReferenceableNode referenceNode) {
        return referenceToLibraryMap.get(referenceNode);
    }

    /**
     * @param id Library ID.
     * @param version Library version.
     * @return Library node with the provided ID and version.
     */
    public LibraryNode getLibraryNodeByIDAndVersion(String id, String version) {
        return idAndVersionToLibraryMap.get(id).get(version);
    }
}
