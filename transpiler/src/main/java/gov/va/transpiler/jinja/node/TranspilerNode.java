package gov.va.transpiler.jinja.node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    private Set<String> operatorsUsed = new LinkedHashSet<>();

    /**
     * @param state Used to keep track of state variables. When a transpiler node is constructed, it should always set itself as the current node.
     */
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
     * @return Returns a list of operators this operator is reliant on.
     */
    public Set<String> getOperatorDependencies() {
        return operatorsUsed;
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
                getOperatorDependencies().add(child.getOperator());
                getOperatorDependencies().addAll(child.getOperatorDependencies());
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
    public String getOperator() {
        return "UnsupportedOperator";
    }

    /**
     * Joins a {@link List} of {@link Segment}s together by adding them all to a top-level segment with joiner segments in between.
     * 
     * @param segmentList {@link Segment}s to join.
     * @param head Text to use as head of the top-level {@link Segment}
     * @param tail Text to use as tail of the top-level {@link Segment}
     * @param joiner Text to join {@link Segment}s.
     * @return Top level {@link Segment} containing joined {@link Segment} from {@code segmentList}.
     */
    protected Segment joinSegments(List<Segment> segmentList, String head, String tail, String joiner) {
        var topLevel = new Segment();
        topLevel.setHead(head);
        boolean first = true;
        for (var segment: segmentList) {
            if (!first) {
                var joinSegment = new Segment(joiner);
                topLevel.addChild(joinSegment);
            } else {
                first = false;
            }
            topLevel.addChild(segment);
        }
        topLevel.setTail(tail);
        return topLevel;
    }

    /**
     * @return All simple details about this node, including which operator it represents in the intermediate AST.
     */
    protected Map<String, String> getLiteralArgumentMap() {
        Map<String, String> argumentMap = new LinkedHashMap<>();
        argumentMap.put("'operator'", getOperator());
        return argumentMap;
    }

    /**
     * @return List of details about this node that refer to individual nodes. Includes this node's 'child' if it can only have a maximum of one generic child, or this node's 'left' and 'right' members if it can only have a maximum of two.
     */
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

    /**
     * @return List of details about this node that refer to lists of nodes. Includes this nodes 'children' if it can have three or more generic children.
     */
    protected Map<String, List<TranspilerNode>> getNodeListArgumentMap() {
        Map<String, List<TranspilerNode>> argumentMap = new LinkedHashMap<>();
        if (allowedNumberOfChildren() == UNLIMITED_CHILDREN || allowedNumberOfChildren() > 2) {
            argumentMap.put("'children'", getChildren());
        }
        return argumentMap;
    }

    /**
     * @param literalArgumentMap Simple details about a node.
     * @param nodeArgumentMap Details about a node that refer to other nodes.
     * @param nodeListArgumentMap Details about a node that refer to lists of other nodes.
     * @return Collation of all details provided.
     */
    protected List<Segment> getArgumentList(Map<String, String> literalArgumentMap, Map<String, TranspilerNode> nodeArgumentMap, Map<String, List<TranspilerNode>> nodeListArgumentMap) {
        List<Segment> argumentList = new ArrayList<>();

        // Render literal arguments as jinja dictionary entries
        argumentList.addAll(literalArgumentMap.entrySet().stream().map(entry -> new Segment(entry.getKey() + ": " + entry.getValue())).collect(Collectors.toList()));

        // Render arguments that are nodes as jinja dictionary entries
        argumentList.addAll(nodeArgumentMap.entrySet().stream().map(entry -> {
            var nodeArgumentSegment = new Segment(entry.getKey() + ": ");
            nodeArgumentSegment.addChild(entry.getValue() == null ? new Segment("none") : entry.getValue().toSegment());
            return nodeArgumentSegment;
        }).collect(Collectors.toList()));

        // Render arguments that are lists of nodes as jinja dictionary entries
        argumentList.addAll(nodeListArgumentMap.entrySet().stream().map(entry -> {
            var nodeListArgumentSegment = new Segment(entry.getKey() + ": ");
            var segmentList = entry.getValue().stream().map(node -> node.toSegment()).collect(Collectors.toList());
            var joinedSegment = joinSegments(segmentList, "[", "]", ", ");
            nodeListArgumentSegment.addChild(joinedSegment);
            return nodeListArgumentSegment;
        }).collect(Collectors.toList()));

        return argumentList;
    }

    /**
     * @return This node rendered as a {@link Segment}.
     */
    public Segment toSegment() {
        return joinSegments(getArgumentList(getLiteralArgumentMap(), getNodeArgumentMap(), getNodeListArgumentMap()), "{ ", " }", ", ");
    }

    /**
     * @return A {@link String} describing this node's relative location in the file system.
     */
    public String getTargetFileLocation() {
        return getParent() == null ? "" : getParent().getTargetFileLocation();
    }
}
