package gov.va.transpiler.jinja.state;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.cqframework.cql.elm.tracking.Trackable;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.trackable.element.LibraryNode;
import gov.va.transpiler.jinja.node.trackable.element.expressiondef.ExpressionDefNode;
import gov.va.transpiler.jinja.standards.Standards;

public class State {

    private String context = Standards.UNFILTERED_CONTEXT;
    private Map<String, Map<String, LibraryNode>> libraryMap = new LinkedHashMap<>();
    private LibraryNode currentLibrary = null;
    private TranspilerNode currentNode = null;
    private Trackable currentCQLNode = null;
    private Map<LibraryNode, Set<ExpressionDefNode<?>>> modelTracking = new LinkedHashMap<>();

    /**
     * @return If the current CQL context is null, returns {@link Standards#UNFILTERED_CONTEXT}. Otherwise returns the current CQL context.
     */
    public String getContext() {
        return context == null ? Standards.UNFILTERED_CONTEXT : context;
    }

    /**
     * @return Sets the current CQL context.
     */
    public void setContext(String context) {
        this.context = context;
    }

    public void setCurrentNode(TranspilerNode node) {
        currentNode = node;
    }

    public TranspilerNode getCurrentNode() {
        return currentNode;
    }

    public void setCurrentCQLNode(Trackable node) {
        currentCQLNode = node;
    }

    public Trackable getCurrentCQLNode() {
        return currentCQLNode;
    }

    public void setCurrentLibrary(String id, String version, LibraryNode libraryNode) {
        var versionedMap = libraryMap.getOrDefault(id, new LinkedHashMap<>());
        versionedMap.put(version, libraryNode);
        libraryMap.put(id, versionedMap);
        currentLibrary = libraryNode;
    }

    public LibraryNode getCurrentLibrary() {
        return currentLibrary;
    }

    public LibraryNode getLibraryByIdAndVersion(String id, String version) {
        return libraryMap.get(id).get(version);
    }

    public void addCurrentExpressionToModelTracking(ExpressionDefNode<?> model) {
        var modelSet = modelTracking.getOrDefault(getCurrentLibrary(), new LinkedHashSet<>());
        modelSet.add(model);
        modelTracking.put(getCurrentLibrary(), modelSet);
    }

    public Map<LibraryNode, Set<ExpressionDefNode<?>>> getModelTracking() {
        return modelTracking;
    }
}
