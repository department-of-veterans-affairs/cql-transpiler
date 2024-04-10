package gov.va.transpiler.jinja.node.utilityinterfaces;

/**
 * A node that is a reference to another node.
 */
public interface ReferenceNode {
    /**
     * @return The type of node this node references.
     */
    public String referenceType();
    /**
     * @return The name of the node this node references.
     */
    public String referenceName();
}
