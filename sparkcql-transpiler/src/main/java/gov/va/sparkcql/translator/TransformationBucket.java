package gov.va.sparkcql.translator;

import java.util.Set;

import org.hl7.elm.r1.Element;

public abstract class TransformationBucket<T extends Transformation> {

    /**
     * @param node Node to retrieve an applicable transformation for.
     * @param parentNode Parent node of the node being checked.
     * @return A translation such that {@link Transformation#appliesToNode(Element, Element)} is true. Null if no translations apply to the current node.
     */
    public abstract T pullTransformationFromBucket(Element node, Element parentNode);

    /**
     * @param transformations Transformations to add to this {@link TransformationBucket}.
     */
    public abstract void addTransformations(Set<T> transformations);
}
