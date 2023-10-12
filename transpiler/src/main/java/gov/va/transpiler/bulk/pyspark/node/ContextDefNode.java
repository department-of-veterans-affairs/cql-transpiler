package gov.va.transpiler.bulk.pyspark.node;

import gov.va.transpiler.node.TerminalNode;

public class ContextDefNode extends TerminalNode {

    private final String context;

    public ContextDefNode(String context) {
        this.context = context;
    }

    @Override
    public String asOneLine() {
        return "context = " + context;
    }
}
