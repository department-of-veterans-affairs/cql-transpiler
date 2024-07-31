package gov.va.transpiler.jinja.node.utilityinterfaces;

/**
 * A node that can be referenced.
 */
public interface ReferenceableNode {
    /**
     * @return This node's unique name.
     */
    public String referenceName();
}
