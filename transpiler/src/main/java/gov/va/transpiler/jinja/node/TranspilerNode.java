package gov.va.transpiler.jinja.node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.standards.Standards;
import gov.va.transpiler.jinja.state.State;

public class TranspilerNode {


    protected static final int UNLIMITED_CHILDREN = -1;
    protected enum Type {
        DISABLED,
        SIMPLE,
        ENCAPSULATED_SIMPLE,
        TABLE,
        COLLECTED_TABLE,
        COLUMN_REFERENCE
    }

    private TranspilerNode parent;
    private List<TranspilerNode> children = new ArrayList<>();

    public TranspilerNode(State state) {
        state.setCurrentNode(this);
    }

    /**
     * @param parent This node's parent, to allow transversal.
     */
    public void setParent(TranspilerNode parent) {
        this.parent = parent;
    }

    /**
     * @return This node's parent.
     */
    public TranspilerNode getParent() {
        return parent;
    }

    protected int allowedNumberOfChildren() {
        return UNLIMITED_CHILDREN;
    }

    public void addChild(TranspilerNode child) throws UnsupportedChildNodeException {
        if (child.getType() != Type.DISABLED) {
            if (allowedNumberOfChildren() == UNLIMITED_CHILDREN || getChildren().size() < allowedNumberOfChildren()) {
                children.add(child);
            } else {
                throw new UnsupportedChildNodeException(this, child);
            }
        }
    }

    public List<TranspilerNode> getChildren() {
        return Collections.unmodifiableList(children);
    }

    protected TranspilerNode getChild() {
        return getChildren().get(0);
    }

    protected TranspilerNode getLeft() {
        return getChildren().get(0);
    }

    protected TranspilerNode getRight() {
        return getChildren().get(1);
    }

    public Type getType() {
        return Type.SIMPLE;
    }

    public Segment toSegment() {
        return toSegmentWithJoinedChildren(getChildren(), getName() + "(", ")", "", "", ",");
    }

    protected String getName() {
        return "UnsupportedNode";
    }

    protected Segment toSegmentWithJoinedChildren(List<TranspilerNode> children, String head, String tail, String childPrefix, String childPostfix, String childJoinerInline) {
        var topLevel = new Segment();
        topLevel.setHead(head);
        switch(children.size()) {
            case 0:
                break;
            case 1:
                topLevel.addChild(childToSegment(children.get(0)));
                break;
            default:
                for (int i = 0; i < children.size(); i++) {
                    // Prefix
                    var prefixSegment = new Segment();
                    prefixSegment.setHead(childPrefix);
                    topLevel.addChild(prefixSegment);

                    // Child
                    topLevel.addChild(childToSegment(children.get(i)));

                    // Postfix
                    var postfixSegment = new Segment();
                    boolean last = i == children.size() - 1;
                    postfixSegment.setHead(last ? childPostfix : childPostfix + (childJoinerInline));
                    topLevel.addChild(postfixSegment);
                }
                break;
        }
        topLevel.setTail(tail);
        return topLevel;
    }

    protected Segment childToSegment(TranspilerNode child) {
        return child.toSegment();
    }

    protected Segment childToSegmentCollectTable(TranspilerNode child) {
        var collectSegment = new Segment();
        collectSegment.setHead(Standards.MACRO_FILE_NAME + "." + "Collect(");
        collectSegment.setTail(")");
        collectSegment.addChild(child.toSegment());
        return collectSegment;
    }

    protected Segment childToSegmentEncapsulateSimple(TranspilerNode child) {
        var collectSegment = new Segment();
        collectSegment.setHead(Standards.MACRO_FILE_NAME + "." + "Encapsulate(");
        collectSegment.setTail(")");
        collectSegment.addChild(child.toSegment());
        return collectSegment;
    }

    public String getTargetFileLocation() {
        return getParent() == null ? "" : getParent().getTargetFileLocation();
    }
}
