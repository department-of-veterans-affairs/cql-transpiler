package gov.va.transpiler.bulk.pyspark.node;

import gov.va.transpiler.node.TerminalNode;

public class RetrieveNode extends TerminalNode {

    private final String retrieval;
    private final String modelSource;

    public RetrieveNode(String retrieval, String modelSource) {
        this.retrieval = retrieval;
        this.modelSource = modelSource;
    }

    @Override
    public String asOneLine() {
        return "retrieve(spark, models('" + modelSource + "')['" + retrieval + "'])";
    }
}
