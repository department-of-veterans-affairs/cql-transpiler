package gov.va.transpiler.bulk.pyspark.output;

import gov.va.transpiler.output.OutputWriter;

// TODO: print as a StructField
public class TupleElementNode extends NameValueNode {
    @Override
    public String asOneLine() {
        String valueAString = getValue() == null ? "None" : getValue().asOneLine();
        if (valueAString != null) {
            return getName() + ": " + getValue().asOneLine();
        }
        return null;
    }

    @Override
    public boolean print(OutputWriter outputWriter) {
        if (asOneLine() != null) {
            return super.print(outputWriter);
        }
        outputWriter.addLine(getName());
        outputWriter.raiseIndentLevel();
        getValue().print(outputWriter);
        outputWriter.lowerIndentLevel();
        return true;
    }
}
