package gov.va.transpiler.jinja.node;

/**
 * Thrown on attempts to add an invalid child to a node. If you see this exception, there is a problem with the transpilation logic.
 */
public class UnsupportedChildNodeException extends RuntimeException {
    
    private TranspilerNode parent;
    private TranspilerNode child;

    public UnsupportedChildNodeException(TranspilerNode parent, TranspilerNode child) {
        this.parent = parent;
        this.child = child;
    }

    @Override
    public String toString() {
        return "Parent TranspilerNode ["  + parent + "] does not support child [" + child + "].";
    }
}
