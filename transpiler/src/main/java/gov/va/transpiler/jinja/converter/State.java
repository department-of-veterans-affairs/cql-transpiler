package gov.va.transpiler.jinja.converter;

import java.util.Stack;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.ary.LibraryNode;

public class State {
    private TranspilerNode currentNode = null;
    private Stack<LibraryNode> libraryStack = new Stack<>();

    public TranspilerNode getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(TranspilerNode currentNode) {
        this.currentNode = currentNode;
    }

    public Stack<LibraryNode> getLibraryStack() {
        return libraryStack;
    }

    public void setLibraryStack(Stack<LibraryNode> libraryStack) {
        this.libraryStack = libraryStack;
    }
}
