package gov.va.transpiler.bulk.pyspark.node;

public class OperatorNode extends ParentNode {
    
    private final String operator;

    public OperatorNode(String operator) {
        this.operator = operator;
    }

    @Override
    public String asOneLine() {
        String builder = "";
        boolean first = true;
        for (var child : getChildren()) {
            var childString = child.asOneLine();
            if (childString == null) {
                return null;
            }
            builder += first ? child.asOneLine() : " " + operator + " " + child.asOneLine();
            first = false;
        }
        return builder;
    }
}
