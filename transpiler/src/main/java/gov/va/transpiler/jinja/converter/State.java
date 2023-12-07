package gov.va.transpiler.jinja.converter;

import gov.va.transpiler.jinja.node.TranspilerNode;

public class State {
    private TranspilerNode currentNode = null;

    public TranspilerNode getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(TranspilerNode currentNode) {
        this.currentNode = currentNode;
    }
}
