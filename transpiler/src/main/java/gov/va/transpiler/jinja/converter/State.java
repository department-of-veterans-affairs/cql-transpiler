package gov.va.transpiler.jinja.converter;

import java.util.Stack;

import gov.va.transpiler.jinja.node.TranspilerNode;

public class State {
    private final Stack<TranspilerNode> callStack = new Stack<>();
    private TranspilerNode currentNode = null;

    public Stack<TranspilerNode> getCallStack() {
        return callStack;
    }

    public TranspilerNode getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(TranspilerNode currentNode) {
        this.currentNode = currentNode;
    }
}
