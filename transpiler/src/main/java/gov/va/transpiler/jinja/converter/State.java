package gov.va.transpiler.jinja.converter;

import java.util.Map;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.ary.LibraryNode;
import gov.va.transpiler.jinja.node.unary.FunctionDefNode;

public class State {
    private TranspilerNode currentNode = null;
    private Map<String, TranspilerNode> referenceableNodes;
    private LibraryNode currentLibrary;
    private FunctionDefNode currentFunction;

    public TranspilerNode getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(TranspilerNode currentNode) {
        this.currentNode = currentNode;
    }
}
