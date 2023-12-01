package gov.va.transpiler.jinja.node.utilityinterfaces;

import gov.va.transpiler.jinja.node.TranspilerNode;

/**
 * A node that is a reference to another node.
 */
public interface ReferenceNode<T extends TranspilerNode> {
    /**
     * @return The name of the node this node references.
     */
    public String referencedName();
    /**
     * @return The node that this node references.
     */
    public T referenceTo();
}
