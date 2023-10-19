package gov.va.transpiler.bulk.pyspark.node;

public class WhereNode extends SingleChildNode {

    private String scope;

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public String asOneLine() {
        return scope + ".filter(" + getChildren().get(0).asOneLine() + ")";
    }
}
