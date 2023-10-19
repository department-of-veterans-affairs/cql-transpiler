package gov.va.transpiler.bulk.pyspark.node;

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
