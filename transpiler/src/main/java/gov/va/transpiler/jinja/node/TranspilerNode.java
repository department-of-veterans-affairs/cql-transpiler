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
import gov.va.transpiler.jinja.standards.Standards;
import gov.va.transpiler.jinja.state.State;

/**
 * Parent node for elements of the intermediate AST.
 */
public abstract class TranspilerNode {

    protected static final int UNLIMITED_CHILDREN = -1;

    private final TranspilerNode parent;
    private final List<TranspilerNode> children = new ArrayList<>();
    private final Map<Class<? extends TranspilerNode>, Map<String, TranspilerNode>> namedChildren = new LinkedHashMap<>();
    private final Set<String> operatorsUsed = new LinkedHashSet<>();
    private final Map<String, Set<String>> macrosUsed = new LinkedHashMap<>();

    // this node only posesses a state while it is actively part being used by the transpiler's converter

    /**
     * @param state Used to keep track of state variables. When a transpiler node is constructed, it immediately adds the state's current node as its parent and adds itself to the state as the current node.
     */
    public TranspilerNode(State state) {
        this.parent = state.getCurrentNode();
        state.setCurrentNode(this);
    }

    /**
     * @return This node's parent.
     */
    public TranspilerNode getParent() {
        return parent;
    }

    /**
     * Searches for a parent to this node that inherits from the specified class.
     * @param clazz class of parent to search for.
     * @return a parent of this node that inherits from the specified class.
     */
    public TranspilerNode getParentOfClass(Class<? extends TranspilerNode> clazz) {
        var parent = getParent();
        if (parent == null) {
            return null;
        }
        if (clazz.isInstance(parent)) {
            return parent;
        }
        while (parent != null) {
            if (clazz.isAssignableFrom(parent.getClass())) {
                return parent;
            }
            parent = parent.getParent();
        }
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
    protected Set<String> getOperatorDependencies() {
        return operatorsUsed;
    }

    /**
     * @return Returns a map of macro file/macro pairs this operator is reliant on
     */
    protected Map<String, Set<String>> getMacroDependencies() {
        return macrosUsed;
    }

    public void addMacroToMacroDependencies(String macroFile, String macroName) {
        var macroSetForFile = getMacroDependencies().getOrDefault(macroFile, new LinkedHashSet<>());
        macroSetForFile.add(macroName);
        getMacroDependencies().put(macroFile, macroSetForFile);
    }

    public void processChildDependencies(TranspilerNode child) {
        if (child != null && child.isEnabled()) {
            getOperatorDependencies().add(child.getOperator());
            getOperatorDependencies().addAll(child.getOperatorDependencies());
            child.getMacroDependencies().entrySet().forEach(macroSetEntry -> {
                for (var macro : macroSetEntry.getValue()) {
                    addMacroToMacroDependencies(macroSetEntry.getKey(), macro);
                }
            });
        }
    }

    /**
     * Adds a child node to this node.
     * 
     * @param child Child to add.
     * @throws InvalidChildNodeException Thrown if a child cannot be added to this node.
     */
    public void addChild(TranspilerNode child) throws InvalidChildNodeException {
        if (this == child || child == null) {
            throw new InvalidChildNodeException(this, child);
        } else if (!child.isEnabled()) {
            // Do nothing with disabled nodes
        } else if (allowedNumberOfChildren() == UNLIMITED_CHILDREN || getChildren().size() < allowedNumberOfChildren()) {
            children.add(child);
            processChildDependencies(child);
        } else {
            throw new InvalidChildNodeException(this, child);
        }
    }

    /**
     * @return The list of generic children for this node. This list should never have more than {@link #allowedNumberOfChildren()} children.
     */
    public List<TranspilerNode> getChildren() {
        return Collections.unmodifiableList(children);
    }

    /**
     * @return gets all named children of this node.
     */
    protected Map<Class<? extends TranspilerNode>, Map<String, TranspilerNode>> getNamedChildren() {
        return namedChildren;
    }

    /**
     * @return If this child has one or more generic children, this returns the first generic child added to this node. Otherwise this returns null.
     */
    protected TranspilerNode getChild() {
        return getChildren().isEmpty() ? null : getChildren().get(0);
    }

    public void addNamedChild(String name, TranspilerNode child) {
        var classMap = namedChildren.getOrDefault(child.getClass(), new LinkedHashMap<>());
        classMap.put(name, child);
        namedChildren.put(child.getClass(), classMap);
    }

    public TranspilerNode getChildByNameAndType(String name, Class<? extends TranspilerNode> clazz) {
        return namedChildren.getOrDefault(clazz, new LinkedHashMap<>()).get(name);
    }

    public TranspilerNode getNamedChildOfClassFromParentOfOtherClass(String nameOfChild, Class<? extends TranspilerNode> classOfChild, Class<? extends TranspilerNode> classOfParent) {
        var parent = getParentOfClass(classOfParent);
        while (parent != null) {
            var child = parent.getChildByNameAndType(nameOfChild, classOfChild);
            if (child != null) {
                return child;
            }
            parent = parent.getParent();
        }
        throw new NullPointerException("No valid child node found");
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
        argumentMap.put("'operator'", Standards.ENVIRONMENT_NAME + "." + sanitizeNameForJinja(getOperator()));
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
     * @return List of details about this node that refer to lists of nodes. Includes this node's 'children' if it can have three or more generic children.
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
        argumentList.addAll(nodeArgumentMap
            .entrySet()
            .stream()
            .filter(entry -> entry.getValue() != null)
            .map(entry -> {
                var nodeArgumentSegment = new Segment(entry.getKey() + ": ");
                nodeArgumentSegment.addChild(entry.getValue().toSegment());
                return nodeArgumentSegment;
            }).collect(Collectors.toList()));

        // Render arguments that are lists of nodes as jinja dictionary entries
        argumentList.addAll(nodeListArgumentMap
            .entrySet()
            .stream()
            .filter(entry -> entry.getValue() != null && !entry.getValue().isEmpty())
            .map(entry -> {
                var nodeListArgumentSegment = new Segment(entry.getKey() + ": ");
                var segmentList = entry.getValue().stream().map(node -> node.toSegment()).collect(Collectors.toList());
                var joinedSegment = joinSegments(segmentList, "[", "]", ", ");
                nodeListArgumentSegment.addChild(joinedSegment);
                return nodeListArgumentSegment;
            })
            .collect(Collectors.toList()));
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

    /**
     * @param name Name of variable to sanitize.
     * @return Sanatized name that can work as a jinja variable name.
     */
    public String sanitizeNameForJinja(String name) {
        return name.replace(" ", "_").replace(".", "__").replace("(", "_LP_").replace(")", "_RP_").replace(",", "_COMMA_").replace(":", "_COLON_").replace("-", "_DASH_").replace("/", "_SLASH_").replace(">", "_GT_").replace("<", "_LT_").replace("=", "_EQ_").replace("!", "_BANG_").replace("?", "_QMARK_").replace("&", "_AMP_").replace("|", "_PIPE_").replace("+", "_PLUS_").replace("*", "_STAR_").replace("%", "_PERCENT_").replace("^", "_CARET_").replace("~", "_TILDE_").replace("`", "_BACKTICK_").replace("'", "_SINGLEQUOTE_").replace("\"", "_DOUBLEQUOTE_");
    }
}
