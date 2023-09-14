package gov.va.sparkcql.translator;

import java.util.Set;

import org.hl7.elm.r1.Element;

public abstract class TransformationBucket {
    /**
     * @param node Node to check for an applicable transformation of.
     * @param parentNode Parent node of the node being checked.
     * @return A translation such that {@link Transformation#appliesToNode(Element, Element)} is true. Null if no translations apply to the current node.
     */
    public abstract Transformation pullTransformationFromBucket(Element node, Element parentNode);
    /**
     * @param transformations A list of translations to add to this {@link TransformationBucket}.
     */
    public abstract void addTransformations(Set<Transformation> transformations);
}
