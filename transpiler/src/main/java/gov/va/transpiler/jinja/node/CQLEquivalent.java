package gov.va.transpiler.jinja.node;

import org.cqframework.cql.elm.tracking.Trackable;

public abstract class CQLEquivalent<T extends Trackable> implements TranspilerNode {

    private T cqlEquivalent;

    public CQLEquivalent(T t) {
        cqlEquivalent = t;
    }

    public T getCqlEquivalent() {
        return cqlEquivalent;
    }
}
