package gov.va.transpiler.bulk;

import java.util.LinkedHashSet;
import java.util.Set;

import org.hl7.elm.r1.Element;

import gov.va.transpiler.TransformationLoader;

public class BulkTransformationLoader extends TransformationLoader<BulkTransformation<? extends Element>> {

    private Set<BulkTransformation<? extends Element>> transformations;

    public BulkTransformationLoader() {
        transformations = new LinkedHashSet<>();
    }

    @Override
    public BulkTransformation<? extends Element> loadTransformation(Element node, Element parentNode) {
        if (node == null) {
            return null;
        }
        for (BulkTransformation<? extends Element> transformation : transformations) {
            if (transformation.appliesToNode(node, parentNode)) {
                return transformation;
            }
        }
        return null;
    }

    @Override
    public void registerTransformations(Set<BulkTransformation<? extends Element>> transformationsToAdd) {
        transformations.addAll(transformationsToAdd);
    }
}
