package gov.va.transpiler.bulk.pyspark.output;

import gov.va.transpiler.output.OutputNode;
import gov.va.transpiler.output.OutputWriter;

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
