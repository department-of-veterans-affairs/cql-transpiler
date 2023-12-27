package gov.va.transpiler.jinja.state;

import java.util.LinkedHashMap;
import java.util.Map;

import gov.va.transpiler.jinja.node.ReferenceNode;
import gov.va.transpiler.jinja.node.ReferenceableNode;
import gov.va.transpiler.jinja.node.TranspilerNode;

public class State {
    private TranspilerNode currentNode = null;
    private String context = null;
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
        this.context = context;
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
