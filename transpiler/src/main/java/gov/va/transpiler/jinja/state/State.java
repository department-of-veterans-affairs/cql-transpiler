package gov.va.transpiler.jinja.state;

import java.util.LinkedHashMap;
import java.util.Map;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.utilityinterfaces.ReferenceNode;
import gov.va.transpiler.jinja.node.utilityinterfaces.ReferenceableNode;

public class State {

    private static final String UNFILTERED_CONTEXT = "Unfiltered";
    private TranspilerNode currentNode = null;
    private String context = UNFILTERED_CONTEXT;
    private Map<String, Map<String, ReferenceableNode>> references = new LinkedHashMap<>();

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
        var typeReferenceMap = references.getOrDefault(referenceableNode.referenceType(), new LinkedHashMap<>());
        typeReferenceMap.put(referenceableNode.referenceName(), referenceableNode);
        references.putIfAbsent(referenceableNode.referenceType(), typeReferenceMap);
    }

    public ReferenceableNode getReference(ReferenceNode referenceNode) {
        return references.get(referenceNode.referenceType()).get(referenceNode.referenceName());
    }
}
