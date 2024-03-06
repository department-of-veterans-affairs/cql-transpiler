package gov.va.transpiler.jinja.node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.printing.Segment.PrintType;
import gov.va.transpiler.jinja.state.State;

public class TranspilerNode {

    protected static final int UNLIMITED_CHILDREN = -1;

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

    protected boolean isEnabled() {
        return true;
    }

    public void addChild(TranspilerNode child) throws UnsupportedChildNodeException {
        if (child.isEnabled()) {
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

    protected String getName() {
        return "UnsupportedNode";
    }

    protected Segment joinSegments(List<Segment> segmentList, String head, String tail, String joiner) {
        var topLevel = new Segment();
        topLevel.setHead(head);
        boolean first = true;
        for (var segment: segmentList) {
            if (first) {
                first = false;
            } else {
                var joinSegment = new Segment();
                joinSegment.setHead(joiner);
                topLevel.addChild(joinSegment);
            }

            topLevel.addChild(segment);
        }
        topLevel.setTail(tail);
        return topLevel;
    }

    protected Segment joinChildren(List<? extends TranspilerNode> children, String head, String tail, String childPrefix, String childPostfix, String childJoinerInline) {
        var topLevel = new Segment();
        topLevel.setHead(head);
        switch(children.size()) {
            case 0:
                break;
            case 1:
                topLevel.addChild(children.get(0).toSegment());
                break;
            default:
                for (int i = 0; i < children.size(); i++) {
                    // Prefix
                    var prefixSegment = new Segment();
                    prefixSegment.setHead(childPrefix);
                    topLevel.addChild(prefixSegment);

                    // Child
                    topLevel.addChild(children.get(i).toSegment());

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

    protected Segment argumentToSegment(String name, String value) {
        return new Segment("'" + name + "': '" + value + "'");
    }

    protected Map<String, String> getStringArgumentMap() {
        Map<String, String> argumentMap = new LinkedHashMap<>();
        argumentMap.put("name", getName());
        return argumentMap;
    }

    protected List<Segment> getArgumentList(Map<String, String> stringArgumentMap, List<TranspilerNode> children) {
        List<Segment> argumentList = new ArrayList<>();
        for (var entry : stringArgumentMap.entrySet()) {
            argumentList.add(argumentToSegment(entry.getKey(), entry.getValue()));
        }
        var valuesSegment = new Segment("'values': [", "]", PrintType.Inline);
        valuesSegment.addChild(joinChildren(children, "", "", "", "", ", "));
        argumentList.add(valuesSegment);
        return argumentList;
    }

    public Segment toSegment() {
        return joinSegments(getArgumentList(getStringArgumentMap(), getChildren()), "{", "}", ", ");
    }

    public String getTargetFileLocation() {
        return getParent() == null ? "" : getParent().getTargetFileLocation();
    }
}
