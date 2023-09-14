package gov.va.sparkcql.translator;

import java.util.Collections;
import java.util.List;

import org.hl7.elm.r1.Element;

public class Transformer {
    private TransformationBucket transformationBucket;
    public Transformer(TransformationBucket transformationBucket) {
        this.transformationBucket = transformationBucket;
    }

    public void applyTransformation(Element currentNode, Element parentNode, int depth) {
        // If depth is negative, descend until reaching a child node without any leaves
        if (depth == 0 || getChildrenOfNode(currentNode).isEmpty()) {
            return;
        }
        for (Element child : getChildrenOfNode(currentNode)) {
            applyTransformation(child, parentNode, depth - 1);
            Transformation transformation;
            while ((transformation = transformationBucket.pullTransformationFromBucket(currentNode, parentNode)) != null) {
                    int depthToCheck = transformation.transform(child, currentNode);
                    applyTransformation(child, parentNode, depthToCheck);
            }
        }
    }

    protected List<Element> getChildrenOfNode(Element library) {
        // TODO
        return Collections.emptyList();
    }
}
