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
        COLUMN_REFERENCE,
        LAZY_EVALUATION
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
        return getChildren().isEmpty() ? null : getChildren().get(0);
    }

    protected TranspilerNode getLeft() {
        return getChildren().isEmpty() ? null : getChildren().get(0);
    }

    protected TranspilerNode getRight() {
        return getChildren().isEmpty() ? null : getChildren().get(1);
    }

    public TranspilerNode getChildByReference(String nameOrIndex) {
        return getChild();
    }

    public Type getType() {
        return Type.SIMPLE;
    }

    public Segment toSegment() {
        return toSegmentWithJoinedChildren(getChildren(), getName() + "(", ")", "", "", ", ");
    }

    protected String getName() {
        return "UnsupportedNode";
    }

    protected Segment toSegmentWithJoinedChildren(List<? extends TranspilerNode> children, String head, String tail, String childPrefix, String childPostfix, String childJoinerInline) {
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

    protected Segment collectSegment(String context, Segment segment) {
        var collectSegment = new Segment();
        collectSegment.setHead(Standards.MACRO_FILE_NAME + "." + "Collect(");
        var contextChild = new Segment();
        contextChild.setHead("'" + context + "', ");
        collectSegment.addChild(contextChild);
        collectSegment.addChild(segment);
        collectSegment.setTail(")");
        return collectSegment;
    }
    protected Segment childToSegmentCollectTable(String context, TranspilerNode child) {
        return collectSegment(context, child.toSegment());
    }

    protected Segment decollectSegment(String context, Segment segment) {
        var decollectSegment = new Segment();
        decollectSegment.setHead(Standards.MACRO_FILE_NAME + "." + "Decollect(");
        var contextChild = new Segment();
        contextChild.setHead("'" + context + "', ");
        decollectSegment.addChild(contextChild);
        decollectSegment.addChild(segment);
        decollectSegment.setTail(")");
        return decollectSegment;
    }

    protected Segment childToSegmentDecollectTable(String context, TranspilerNode child) {
        return decollectSegment(context, child.toSegment());
    }

    protected Segment encapsulateSegment(Segment segment) {
        var encapsulateSegment = new Segment();
        encapsulateSegment.setHead(Standards.MACRO_FILE_NAME + "." + "Encapsulate(");
        encapsulateSegment.setTail(")");
        encapsulateSegment.addChild(segment);
        return encapsulateSegment;
    }

    protected Segment childToSegmentEncapsulateSimple(TranspilerNode child) {
        return encapsulateSegment(child.toSegment());
    }

    public String getTargetFileLocation() {
        return getParent() == null ? "" : getParent().getTargetFileLocation();
    }
}
