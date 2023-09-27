package gov.va.transpiler.bulk.pyspark.output;

import gov.va.transpiler.output.OutputWriter;

public class TupleNode extends MultiChildNode {

    @Override
    public String asOneLine() {
        String builder = "{";
        boolean first = true;
        for (var child : getChildren()) {
            var childAsString = child.asOneLine();
            if (childAsString == null) {
                return null;
            }
            builder += first ? "" : ", ";
            first = false;
            builder += childAsString;
        }
        builder += "}";
        return builder;
    }

    @Override
    public boolean print(OutputWriter outputWriter) {
        return super.print(outputWriter);
    }
}
