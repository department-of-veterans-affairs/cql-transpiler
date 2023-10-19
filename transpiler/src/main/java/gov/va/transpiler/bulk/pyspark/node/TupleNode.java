package gov.va.transpiler.bulk.pyspark.node;

import gov.va.transpiler.node.OutputWriter;

public class TupleNode extends ParentNode {

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
