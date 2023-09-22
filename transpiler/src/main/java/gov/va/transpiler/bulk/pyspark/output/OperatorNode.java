package gov.va.transpiler.bulk.pyspark.output;

public class OperatorNode extends MultiChildNode {
    
    private final String operator;

    public OperatorNode(String operator) {
        this.operator = operator;
    }

    @Override
    public String asOneLine() {
        String builder = "";
        boolean first = true;
        for (var child : getChildren()) {
            builder += first ? child.asOneLine() : " " + operator + " " + child.asOneLine();
            first = false;
        }
        return builder;
    }
}
