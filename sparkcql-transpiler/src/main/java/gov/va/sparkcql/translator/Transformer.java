package gov.va.sparkcql.translator;

import org.cqframework.cql.elm.tracking.Trackable;
import org.cqframework.cql.elm.visiting.ElmBaseLibraryVisitor;
import org.hl7.elm.r1.Element;

public class Transformer extends ElmBaseLibraryVisitor<Integer, TransformationState>{

    private TransformationBucket<? extends Transformation> transformationBucket;

    public Transformer(TransformationBucket<? extends Transformation> transformationBucket) {
        this.transformationBucket = transformationBucket;
    }

    /**
     * Semantics of return:
     * if -1, transverse to the bottom of the tree
     * if 0, cease transversal immediately and return
     * if 1+, execute logic for this node
     * 
     * Visits the current node using the state. Returns an integer 'a' such that:
     * 
     * 1. If the state cannot go any deeper, exit visitElement, returning an Integer 'i' such that if {@link TransformationState#enterSubtreeAndSetMaximumDepth(Integer)} is set to 'i',
     *  {@link TransformationState#goDeeper(Integer)} returns true;
     * 2. recursively visit lower-level elements using super.visitElement
     */
    @Override
    public Integer visitElement(Element nodeToVisit, TransformationState state) {
        if (!state.goDeeper()) {
            return 0;
        }
        int maxDepthForParentNodeToRevisit = 0;
        int maxDepthForCurrentNodeToRevisit = 0;
        // visit this node's children
        do {
            // Sets the current node as a parent node, so that if visitElement is called for one of this node's child nodes, transformations can see this node and modify it to include any new children
            state.putNode(nodeToVisit);
            // If we modify which nodes are children of this node, this node will have to revisit its children
            maxDepthForCurrentNodeToRevisit = super.visitElement(nodeToVisit, state);
            state.popNode();
            state.enterSubtreeSettingMaximumDepth(maxDepthForCurrentNodeToRevisit);
            maxDepthForParentNodeToRevisit = Math.max(maxDepthForCurrentNodeToRevisit, visitElement(nodeToVisit, state));
            state.exitSubtree();
        } while (maxDepthForCurrentNodeToRevisit != 0);
        // Once we've handled the transformations of all the children of this node, apply the first relevant transformation this node, then return to the parent node.
        // The parent node will handle calling this node multiple times if it needs multiple transformations
        var transformation = transformationBucket.pullTransformationFromBucket(nodeToVisit, state.peekNode());
        return Math.max(maxDepthForParentNodeToRevisit, transformation == null ? 0 : transformation.transform(nodeToVisit, state.peekNode()));
    }

    @Override
    protected Integer defaultResult(Trackable elm, TransformationState context) {
        return 0;
    }
}
