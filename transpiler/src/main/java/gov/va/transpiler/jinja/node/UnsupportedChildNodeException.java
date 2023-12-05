package gov.va.transpiler.jinja.node;

/**
 * Thrown on attempts to add an invalid child to a node. If you see this exception, there is a problem with the transpilation logic.
 */
public class UnsupportedChildNodeException extends RuntimeException {
    
    private TranspilerNode parent;
    private TranspilerNode unsupportedChild;

    public UnsupportedChildNodeException(TranspilerNode parent, TranspilerNode unsupportedChild) {
        this.parent = parent;
        this.unsupportedChild = unsupportedChild;
    }

    @Override
    public String toString() {
        return "Parent ["  + parent + "] does not support child [" + unsupportedChild + "].";
    }
}
