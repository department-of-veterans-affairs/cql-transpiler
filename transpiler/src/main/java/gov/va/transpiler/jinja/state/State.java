package gov.va.transpiler.jinja.state;

import java.util.LinkedHashMap;
import java.util.Map;

import gov.va.transpiler.jinja.node.TranspilerNode;

public class State {
    private TranspilerNode currentNode = null;
    private Map<String, TranspilerNode> references = new LinkedHashMap<>();

    public TranspilerNode getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(TranspilerNode currentNode) {
        this.currentNode = currentNode;
    }

    public  Map<String, TranspilerNode> getReferences() {
        return references;
    }
}
