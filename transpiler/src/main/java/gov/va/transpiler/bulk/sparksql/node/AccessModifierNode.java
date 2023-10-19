package gov.va.transpiler.bulk.sparksql.node;

public class AccessModifierNode extends AbstractNodeNoChildren {

    // Spark SQL doesn't support access modifiers
    @Override
    public String asOneLine() {
        return null;
    }
}
