package gov.va.sparkcql.translator.pyspark;

import java.util.LinkedHashSet;
import java.util.Set;

import org.hl7.elm.r1.Element;

import gov.va.sparkcql.translator.TransformationBucket;

public class PySparkTransformationBucket extends TransformationBucket<PySparkTransformation<? extends Element>> {

    private Set<PySparkTransformation<? extends Element>> transformations;

    public PySparkTransformationBucket() {
        transformations = new LinkedHashSet<>();
    }

    @Override
    public PySparkTransformation<? extends Element> pullTransformationFromBucket(Element node, Element parentNode) {
        if (node == null) {
            return null;
        }
        for (PySparkTransformation<? extends Element> transformation : transformations) {
            if (transformation.appliesToNode(node, parentNode)) {
                return transformation;
            }
        }
        return null;
    }

    @Override
    public void addTransformations(Set<PySparkTransformation<? extends Element>> transformationsToAdd) {
        transformations.addAll(transformationsToAdd);
    }
}
