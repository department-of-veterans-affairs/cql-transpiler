package gov.va.transpiler.bulk.pyspark.output;


import gov.va.transpiler.output.OutputNode;
import gov.va.transpiler.output.OutputWriter;

public class ExpressionDefNode extends NameValueNode {

    @SuppressWarnings("unused")
    /** we don't support access modifiers */
    private AccessModifierNode accessModifier;

    @Override
    public boolean addChild(OutputNode child) {
        if (child instanceof AccessModifierNode){
            accessModifier = (AccessModifierNode) child;
            return true;
        }
        return super.addChild(child);
    }

    @Override
    public String asOneLine() {
        if (getValue() == null || getValue().asOneLine() == null) {
            return null;
        }
        // We don't support access modifiers
        // String builder = accessModifier.asOneLine() + " " + name + " = " + child.asOneLine();
        String builder = getName() == null ? "" : getName() + " = ";
        builder += getValue().asOneLine();
        return builder;
    }

    @Override
    public boolean print(OutputWriter outputWriter) {
        if (getValue() == null) {
            return false;
        }
        if (!super.print(outputWriter)) {
            boolean printResult;
            if (getName() != null) {
                outputWriter.addLine(getName() + " =");
                outputWriter.raiseIndentLevel();
                printResult = getValue().print(outputWriter);
                outputWriter.lowerIndentLevel();
            } else {
                printResult = getValue().print(outputWriter);
            }
            return printResult;
        }
        return true;
    }
}
