package gov.va.transpiler.bulk.pyspark.node;

import gov.va.transpiler.node.OutputWriter;

public class TupleElementNode extends SingleChildNode {

    @Override
    public String asOneLine() {
        if (getName() != null && !getChildren().isEmpty()) {
            return "'" + getName() + "' : " + getChildren().get(0).asOneLine();
        }
        return null;
    }

    @Override
    public boolean print(OutputWriter outputWriter) {
        return super.print(outputWriter);
    }
}
