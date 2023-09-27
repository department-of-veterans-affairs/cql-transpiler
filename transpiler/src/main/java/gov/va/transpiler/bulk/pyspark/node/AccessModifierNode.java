package gov.va.transpiler.bulk.pyspark.node;

import gov.va.transpiler.bulk.pyspark.OutputWriter;
import gov.va.transpiler.node.OutputNode;

public class AccessModifierNode extends OutputNode {
    // We don't support access modifiers

    @Override
    public boolean addChild(OutputNode child) {
        return false;
    }

    @Override
    public String asOneLine() {
        return null;
    }

    @Override
    public boolean print(OutputWriter outputWriter) {
        return false;
    }
}
