package gov.va.transpiler.jinja.node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

/**
 * Parent node for elements of the intermediate AST.
 */
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

    /**
     * Transpiler nodes implementations are allowed to specify whether they can contain a specific or unlimited number of children.See: {@link #UNLIMITED_CHILDREN}
     * 
     * @return Returns the amount of children this node is allowed to add.
     */
    protected int allowedNumberOfChildren() {
        return UNLIMITED_CHILDREN;
    }

    /**
     * Disabled nodes should not be added to the intermediate AST, and should not have their children transversed.
     * 
     * @return Whether a node is enabled.
     */
    protected boolean isEnabled() {
        return true;
    }

    /**
     * Adds a child node to this node.
     * 
     * @param child Child to add.
     * @throws InvalidChildNodeException Thrown if a child cannot be added to this node.
     */
    public void addChild(TranspilerNode child) throws InvalidChildNodeException {
        if (child.isEnabled()) {
            if (allowedNumberOfChildren() == UNLIMITED_CHILDREN || getChildren().size() < allowedNumberOfChildren()) {
                children.add(child);
            } else {
                throw new InvalidChildNodeException(this, child);
            }
        }
    }

    /**
     * @return The list of generic children for this node. This list should never have more than {@link #allowedNumberOfChildren()} children.
     */
    public List<TranspilerNode> getChildren() {
        return Collections.unmodifiableList(children);
    }

    /**
     * @return If this child has one or more generic children, this returns the first generic child added to this node. Otherwise this returns null.
     */
    protected TranspilerNode getChild() {
        return getChildren().isEmpty() ? null : getChildren().get(0);
    }

    /**
     * @return If this child has one or more generic children, this returns the first generic child added to this node. Otherwise this returns null. For use in binary expressions.
     */
    protected TranspilerNode getLeft() {
        return getChildren().isEmpty() ? null : getChildren().get(0);
    }

    /**
     * @return If this child has two or more generic children, this returns the second generic child added to this node. Otherwise this returns null. For use in binary expressions.
     */
    protected TranspilerNode getRight() {
        return getChildren().isEmpty() ? null : getChildren().get(1);
    }

    /**
     * @return Returns what kind of operator this node represents in the intermediate AST.
     */
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

    protected Map<String, String> getLiteralArgumentMap() {
        Map<String, String> argumentMap = new LinkedHashMap<>();
        argumentMap.put("'operator'", getOperator());
        return argumentMap;
    }

    protected Map<String, TranspilerNode> getNodeArgumentMap() {
        Map<String, TranspilerNode> argumentMap = new LinkedHashMap<>();
        if (allowedNumberOfChildren() == 1) {
            argumentMap.put("'child'", getChild());
        } else if (allowedNumberOfChildren() == 2) {
            argumentMap.put("'left'", getLeft());
            argumentMap.put("'right'", getRight());
        }
        return argumentMap;
    }

    protected Map<String, List<TranspilerNode>> getNodeListArgumentMap() {
        Map<String, List<TranspilerNode>> argumentMap = new LinkedHashMap<>();
        if (allowedNumberOfChildren() < 0 || allowedNumberOfChildren() > 2) {
            argumentMap.put("'children'", getChildren());
        }
        return argumentMap;
    }

    protected List<Segment> getArgumentList(Map<String, String> literalArgumentMap, Map<String, TranspilerNode> nodeArgumentMap, Map<String, List<TranspilerNode>> nodeListArgumentMap) {
        List<Segment> argumentList = new ArrayList<>();

        // Render literal arguments as jinja dictionary entries
        argumentList.addAll(literalArgumentMap.entrySet().stream().map(entry -> argumentToSegment(entry.getKey(), entry.getValue())).collect(Collectors.toList()));

        // Render arguments that are nodes as jinja dictionary entries
        argumentList.addAll(nodeArgumentMap.entrySet().stream().map(entry -> {
            var nodeArgumentSegment = new Segment(entry.getKey() + ": ");
            nodeArgumentSegment.addChild(entry.getValue() == null ? new Segment("none") : entry.getValue().toSegment());
            return nodeArgumentSegment;
        }).collect(Collectors.toList()));

        // Render arguments that are lists of nodes as jinja dictionary entries
        argumentList.addAll(nodeListArgumentMap.entrySet().stream().map(entry -> {
            var nodeListArgumentSegment = new Segment(entry.getKey() + ": ");
            nodeListArgumentSegment.addChild(joinTranspilerNodesAsSegment(entry.getValue(), "[", "]", "", "", ", "));
            return nodeListArgumentSegment;
        }).collect(Collectors.toList()));

        return argumentList;
    }

    public Segment toSegment() {
        return joinSegments(getArgumentList(getLiteralArgumentMap(), getNodeArgumentMap(), getNodeListArgumentMap()), "{ ", " }", ", ");
    }

    public String getTargetFileLocation() {
        return getParent() == null ? "" : getParent().getTargetFileLocation();
    }
}
