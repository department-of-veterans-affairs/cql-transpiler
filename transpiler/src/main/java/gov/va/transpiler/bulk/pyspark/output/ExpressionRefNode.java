package gov.va.transpiler.bulk.pyspark.output;

import gov.va.transpiler.output.OutputNode;

public class ExpressionRefNode extends OutputNode {

    private final String reference;

    public ExpressionRefNode(String reference) {
        this.reference = reference;
    }

    @Override
    public String asOneLine() {
        return reference;
    }

}
