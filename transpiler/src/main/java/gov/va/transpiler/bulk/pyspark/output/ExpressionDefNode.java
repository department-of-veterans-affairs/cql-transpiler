package gov.va.transpiler.bulk.pyspark.output;


import gov.va.transpiler.output.OutputNode;
import gov.va.transpiler.output.OutputWriter;

public class ExpressionDefNode extends OutputNode {

    private String name;
    @SuppressWarnings("unused")
    /** we don't support access modifiers */
    private AccessModifierNode accessModifier;
    private OutputNode child;

    @Override
    public boolean addChild(OutputNode child) {
        if (child instanceof AccessModifierNode){
            accessModifier = (AccessModifierNode) child;
            return true;
        } else if (this. child == null) {
            this.child = child;
            return true;
        }

        return false;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String asOneLine() {
        if (child == null || child.asOneLine() == null) {
            return null;
        }
        // We don't support access modifiers
        // String builder = accessModifier.asOneLine() + " " + name + " = " + child.asOneLine();
        String builder = name == null ? "" : name + " = ";
        builder += child.asOneLine();
        return builder;
    }

    @Override
    public boolean print(OutputWriter outputWriter) {
        if (child == null) {
            return false;
        }
        if (!super.print(outputWriter)) {
            if (name != null) {
                outputWriter.addLine(name + " =");
            }
            return child.print(outputWriter);
        }
        return true;
    }
}
