package gov.va.transpiler.jinja.node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import gov.va.transpiler.jinja.printing.Segment;
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

    public void addChild(TranspilerNode child) throws InvalidChildNodeException {
        if (child.isEnabled()) {
            if (allowedNumberOfChildren() == UNLIMITED_CHILDREN || getChildren().size() < allowedNumberOfChildren()) {
                children.add(child);
            } else {
                throw new InvalidChildNodeException(this, child);
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

    protected String getOperator() {
        return "UnsupportedOperator";
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

    protected Segment joinTranspilerNodesAsSegment(List<? extends TranspilerNode> transpilerNodeList, String head, String tail, String childPrefix, String childPostfix, String childJoinerInline) {
        var topLevel = new Segment();
        topLevel.setHead(head);
        switch(transpilerNodeList.size()) {
            case 0:
                break;
            case 1:
                topLevel.addChild(transpilerNodeList.get(0).toSegment());
                break;
            default:
                for (int i = 0; i < transpilerNodeList.size(); i++) {
                    // Prefix
                    var prefixSegment = new Segment();
                    prefixSegment.setHead(childPrefix);
                    topLevel.addChild(prefixSegment);

                    // Child
                    topLevel.addChild(transpilerNodeList.get(i).toSegment());

                    // Postfix
                    var postfixSegment = new Segment();
                    postfixSegment.setHead(i == transpilerNodeList.size() - 1 ? childPostfix : childPostfix + (childJoinerInline));
                    topLevel.addChild(postfixSegment);
                }
                break;
        }
        topLevel.setTail(tail);
        return topLevel;
    }

    protected Segment argumentToSegment(String name, String value) {
        return new Segment(name + ": " + value);
    }

    protected Map<String, String> getSimpleArgumentMap() {
        Map<String, String> argumentMap = new LinkedHashMap<>();
        argumentMap.put("'operator'", getOperator());
        return argumentMap;
    }

    protected Map<String, List<TranspilerNode>> getComplexArgumentMap() {
        Map<String, List<TranspilerNode>> complexArgumentMap = new LinkedHashMap<>();
        complexArgumentMap.put("'children'", getChildren());
        return complexArgumentMap;
    }

    protected List<Segment> getArgumentList(Map<String, String> simpleArgumentMap, Map<String, List<TranspilerNode>> complexArgumentMap) {
        List<Segment> argumentList = new ArrayList<>();

        // Render simple arguments as jinja dictionary entries
        for (var entry : simpleArgumentMap.entrySet()) {
            argumentList.add(argumentToSegment(entry.getKey(), entry.getValue()));
        }

        // Render complex arguments as jinja dictionary entries
        for (var entry: complexArgumentMap.entrySet()) {
            var complexArgumentSegment = new Segment(entry.getKey() + ": ");
            complexArgumentSegment.addChild(joinTranspilerNodesAsSegment(entry.getValue(), "[", "]", "", "", ", "));
            argumentList.add(complexArgumentSegment);
        }

        return argumentList;
    }

    public Segment toSegment() {
        return joinSegments(getArgumentList(getSimpleArgumentMap(), getComplexArgumentMap()), "{", "}", ", ");
    }

    public String getTargetFileLocation() {
        return getParent() == null ? "" : getParent().getTargetFileLocation();
    }
}
