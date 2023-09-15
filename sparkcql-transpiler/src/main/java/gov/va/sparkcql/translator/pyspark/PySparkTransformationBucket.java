package gov.va.sparkcql.translator.pyspark;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import org.hl7.elm.r1.Element;

import gov.va.sparkcql.translator.TransformationBucket;

public class PySparkTransformationBucket extends TransformationBucket<PySparkTransformation<? extends Element>> {

    private HashMap<Class<? extends Element>, Set<PySparkTransformation<? extends Element>>> transformations;

    public PySparkTransformationBucket() {
        transformations = new HashMap<>();
    }

    @Override
    public PySparkTransformation<? extends Element> pullTransformationFromBucket(Element node, Element parentNode) {
        if (node == null) {
            return null;
        }
        Set<PySparkTransformation<? extends Element>> transformationSetforClass = transformations.get(node.getClass());
        if (transformationSetforClass != null) {
            for (PySparkTransformation<? extends Element> transformation : transformationSetforClass) {
                if (transformation.appliesToNode(node, parentNode)) {
                    return transformation;
                }
            }
        }
        return null;
    }

    @Override
    public void addTransformations(Set<PySparkTransformation<? extends Element>> transformationsToAdd) {
        for (PySparkTransformation<? extends Element> transformation : transformationsToAdd) {
            Set<PySparkTransformation<? extends Element>> transformationSetForClass = transformations.get(transformation.transformsClass());
            if (transformationSetForClass == null) {
                transformationSetForClass = new LinkedHashSet<>();
                transformations.put(transformation.transformsClass(), transformationSetForClass);
            }
            transformationSetForClass.add(transformation);
        }
    }
}
