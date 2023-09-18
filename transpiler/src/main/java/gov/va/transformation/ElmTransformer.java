package gov.va.transformation;

import org.cqframework.cql.elm.tracking.Trackable;
import org.cqframework.cql.elm.visiting.ElmBaseLibraryVisitor;
import org.hl7.elm.r1.Element;

/**
 * An {@link ElmBaseLibraryVisitor} that transforms the ELMs it visits using provided transformations.
 * 
 * Visiting an element will return an {@link Integer} representing how many layer of the tree have been transformed.
 */
public class ElmTransformer extends ElmBaseLibraryVisitor<Integer, ElmTransformerState>{

    private TransformationLoader<? extends Transformation> transformationLoader;

    
    public ElmTransformer(TransformationLoader<? extends Transformation> transformationLoader) {
        this.transformationLoader = transformationLoader;
    }

    @Override
    public Integer visitElement(Element nodeToVisit, ElmTransformerState state) {
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
        var transformation = transformationLoader.loadTransformation(nodeToVisit, state.peekNode());
        state.raiseLevel();
        return Math.max(maxDepthForParentNodeToRevisit, transformation == null ? 0 : transformation.transform(nodeToVisit, state.peekNode()));
    }

    @Override
    protected Integer defaultResult(Trackable elm, ElmTransformerState context) {
        return 0;
    }
}
