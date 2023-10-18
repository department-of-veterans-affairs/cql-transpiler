package gov.va.transpiler.bulk.pyspark.node;

import gov.va.transpiler.node.OutputWriter;
import gov.va.transpiler.node.SingleChildNode;

public class SingletonFromNode extends SingleChildNode {

    @Override
    public String asOneLine() {
        // SingletonFrom simply identifies the child element as a singleton, and needs no special support
        return getChildren().get(0).asOneLine();
    }

    @Override
    public boolean print(OutputWriter outputWriter) {
        if (!super.print(outputWriter)) {
            return getChildren().get(0).print(outputWriter);
        }
        return true;
    }
}
