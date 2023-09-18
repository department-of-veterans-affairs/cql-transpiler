package gov.va.transformation;

import java.util.Stack;

import org.hl7.elm.r1.Element;

public class ElmTransformerState {

    private Stack<Integer> depthIndicatorStack;
    private Stack<Element> nodeStack;

    public ElmTransformerState() {
        depthIndicatorStack = new Stack<>();
        nodeStack = new Stack<>();
    }

    /**
     * Goes one level deeper in the tree of nodes.
     * 
     * @return True if the transversal is allowed to interact with the nodes at the current level of the tree. False otherwise.
     */
    public boolean goDeeper() {
        Integer currentLevel = depthIndicatorStack.pop();
        boolean goDeeper = currentLevel != 0;
        if (goDeeper) {
            depthIndicatorStack.push(currentLevel - 1);
        } else {
            depthIndicatorStack.push(currentLevel);
        }
        return goDeeper;
    }

    public void raiseLevel() {
        depthIndicatorStack.push(depthIndicatorStack.pop() + 1);
    }

    public void enterSubtreeSettingMaximumDepth(Integer maximumDepth) {
        depthIndicatorStack.push(maximumDepth);
    }

    public void exitSubtree() {
        depthIndicatorStack.pop();
    }

    public Element peekNode() {
        return nodeStack.peek();
    }

    public void putNode(Element node) {
        nodeStack.add(node);
    }

    public Element popNode() {
        return nodeStack.pop();
    }
}
