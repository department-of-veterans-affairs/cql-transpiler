package gov.va.transpiler.bulk.sparksql.node;

import org.cqframework.cql.elm.tracking.Trackable;

public class AccessModifierNode extends AbstractNodeNoChildren<Trackable> {

    @Override
    public void setCqlNodeEquivalent(Trackable cqlNodeEquivalent) {
        throw new UnsupportedOperationException();
    }

    // Spark SQL doesn't support access modifiers
    @Override
    public String asOneLine() {
        return null;
    }
}
