package gov.va.transpiler;

import java.util.Set;

import org.hl7.elm.r1.Element;

public abstract class TransformationLoader<T extends Transformation> {

    /**
     * @param node Current node being visited.
     * @param parentNode Parent of the node being visited.
     * @return From the {@link Transformation}s registered to this class,
     *  returns a {@link Transformation} such that {@link Transformation#appliesToNode(Element, Element)} for {@code node} and {@code parentNode} is true.
     *  If no registered {@link Transformation} satisfies that condition, returns null.
     */
    public abstract T loadTransformation(Element node, Element parentNode);

    /**
     * @param transformations A {@link Set} of {@link Transformation}s to register for this loader.
     */
    public abstract void registerTransformations(Set<T> transformations);
}
