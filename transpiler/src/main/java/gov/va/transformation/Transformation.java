package gov.va.transformation;

import org.hl7.elm.r1.Element;

public abstract class Transformation {
    /**
     * @param node Node to check
     * @param parentNode Parent of the node to check
     * @return Whether this transformation applies to the current node.
     */
    public abstract boolean appliesToNode(Element node, Element parentNode);

    /**
     * Applies this transformation to {@code node}. May modify both node and parentNode
     * Does nothing if (@see {@link #appliesToNode(Element, Element)}) is false for the given node and parent.
     * @param node Node to transform.
     * @param parentNode Parent of the node to transform
     * @return The depth level of the transformation applied. Minimum 1 if no changes are made to either {@code node} or {@code parentNode}.
     * 2 if the node is modified, and therefore needs to be checked to verify that no more modifications apply to it.
     */
    public abstract int transform(Element node, Element parentNode);
}
