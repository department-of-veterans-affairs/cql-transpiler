package gov.va.transpiler.bulk.pyspark.output;

import gov.va.transpiler.output.OutputNode;

public class LiteralNode extends OutputNode {

    private final String literal;

    public LiteralNode(String literal) {
        this.literal = literal;
    }

    @Override
    public String asOneLine() {
        return literal;
    }
}
