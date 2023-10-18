package gov.va.transpiler.bulk.sparksql.node;

import gov.va.transpiler.node.OutputWriter;
import gov.va.transpiler.node.OutputNode;

public class AccessModifierNode extends OutputNode {
    // Spark SQL doesn't support access modifiers

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
