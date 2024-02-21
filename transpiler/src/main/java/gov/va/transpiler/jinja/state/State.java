package gov.va.transpiler.jinja.state;

import java.util.LinkedHashMap;
import java.util.Map;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.trackable.element.LibraryNode;
import gov.va.transpiler.jinja.node.utilityinterfaces.ReferenceNode;
import gov.va.transpiler.jinja.node.utilityinterfaces.ReferenceableNode;

public class State {

    private static final String UNFILTERED_CONTEXT = "Unfiltered";
    private TranspilerNode currentNode = null;
    private String context = UNFILTERED_CONTEXT;
    private Map<String, Map<String, ReferenceableNode>> typeAndNametoReferenceMap = new LinkedHashMap<>();
    private LibraryNode currentLibraryNode = null;
    private Map<ReferenceableNode, LibraryNode> referenceToLibraryMap = new LinkedHashMap<>();
    private Map<String, Map<String, LibraryNode>> idAndVersionToLibraryMap = new LinkedHashMap<>();

    public TranspilerNode getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(TranspilerNode currentNode) {
        this.currentNode = currentNode;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context == null ? UNFILTERED_CONTEXT : context;
    }

    public void addReference(ReferenceableNode referenceableNode) {
        var typeReferenceMap = typeAndNametoReferenceMap.getOrDefault(referenceableNode.referenceType(), new LinkedHashMap<>());
        typeReferenceMap.put(referenceableNode.referenceName(), referenceableNode);
        referenceToLibraryMap.put(referenceableNode, currentLibraryNode);
        typeAndNametoReferenceMap.putIfAbsent(referenceableNode.referenceType(), typeReferenceMap);
    }

    public ReferenceableNode getReference(ReferenceNode referenceNode) {
        return typeAndNametoReferenceMap.get(referenceNode.referenceType()).get(referenceNode.referenceName());
    }

    public LibraryNode getCurrentLibraryNode() {
        return currentLibraryNode;
    }

    public void setCurrentLibraryAndAddToLibraryMap(LibraryNode currentLibraryNode) {
        this.currentLibraryNode = currentLibraryNode;
        addLibraryNode(getCurrentLibraryNode().getCqlEquivalent().getIdentifier().getId(), getCurrentLibraryNode().getCqlEquivalent().getIdentifier().getVersion(), getCurrentLibraryNode());
    }

    protected void addLibraryNode(String id, String version, LibraryNode libraryNode) {
        var versionMap = idAndVersionToLibraryMap.getOrDefault(version, new LinkedHashMap<>());
        versionMap.put(version, libraryNode);
        idAndVersionToLibraryMap.putIfAbsent(id, versionMap);
    }

    public LibraryNode getLibraryNodeForReference(ReferenceableNode referenceNode) {
        return referenceToLibraryMap.get(referenceNode);
    }

    public LibraryNode getLibraryNodeByIDAndVersion(String id, String version) {
        return idAndVersionToLibraryMap.get(id).get(version);
    }
}
