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
        return super.print(outputWriter);
    }
}
