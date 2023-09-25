package gov.va.transpiler.bulk.pyspark.output;

import gov.va.transpiler.output.OutputWriter;

public class TupleElementNode extends NameValueNode {

    @Override
    public String asOneLine() {
        if (getName().asOneLine() != null && getValue().asOneLine() != null) {
            return "'" + getName().asOneLine() + "' : " + getValue().asOneLine();
        }
        return null;
    }

    @Override
    public boolean print(OutputWriter outputWriter) {
        if (!super.print(outputWriter)) {
            outputWriter.addLine("'" + getName().asOneLine() + "' :");
            outputWriter.raiseIndentLevel();
            boolean printed = getValue().print(outputWriter);
            outputWriter.lowerIndentLevel();
            return printed;
        }
        return true;
    }
}
