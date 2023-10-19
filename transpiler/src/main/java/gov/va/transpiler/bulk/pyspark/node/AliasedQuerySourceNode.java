package gov.va.transpiler.bulk.pyspark.node;

public class AliasedQuerySourceNode extends SingleChildNode {

    private String alias;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String asOneLine() {
        return alias + " = " + getChildren().get(0).asOneLine();
    }
}
