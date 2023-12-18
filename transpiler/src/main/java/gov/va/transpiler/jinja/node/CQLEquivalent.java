package gov.va.transpiler.jinja.node;

import org.cqframework.cql.elm.tracking.Trackable;

import gov.va.transpiler.jinja.node.utility.Containerizer;
import gov.va.transpiler.jinja.standards.Standards;
import gov.va.transpiler.jinja.state.State;

public abstract class CQLEquivalent<T extends Trackable> extends TranspilerNode {

    private T cqlEquivalent;
    protected Containerizer containerizer = new Containerizer();

    public CQLEquivalent(State state, T cqlEquivalent) {
        super(state);
        this.cqlEquivalent = cqlEquivalent;
        if (referenceIs() != null) {
            state.getReferences().put(referenceIs(), this);
        }
    }

    public T getCqlEquivalent() {
        return cqlEquivalent;
    }

    @Override
    public String getScope() {
        var scope = super.getScope();
        // If this node is can be referenced, it creates its own scope.
        if (referenceIs() != null) {
            scope += referenceIs();
            if (getPrintType() == PrintType.Folder) {
                scope += Standards.FOLDER_SEPARATOR;
            }
        }
        return scope;
    }

    /**
     * @return If this node can be externally referenced, return the name it can be referenced by. Otherwise return null.
     */
    public String referenceIs() {
        return null;
    }

    /**
     * @return If this node is a refernce to another, returns the name of that other null. Otherwise returns null.
     */
    public String references() {
        return null;
    }
}
