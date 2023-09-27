package gov.va.transpiler.bulk.pyspark.output;


import gov.va.transpiler.output.OutputNode;
import gov.va.transpiler.output.OutputWriter;

public class ExpressionDefNode extends NameValueNode {

    /** we don't support access modifiers */
    @SuppressWarnings("unused")
    private AccessModifierNode accessModifier;

    public void setAccessModifier(AccessModifierNode accessModifier) {
        this.accessModifier = accessModifier;
    }

    @Override
    public boolean addChild(OutputNode child) {
        if (child instanceof AccessModifierNode){
            setAccessModifier((AccessModifierNode) child);
            return true;
        }
        return super.addChild(child);
    }

    @Override
    public String asOneLine() {
        return null;
    }

    @Override
    public boolean print(OutputWriter outputWriter) {
        String functionDefinition = "def " + getName() + "(context: CQLModel, data: CQLDataFrame):";
        if (outputWriter.isCurrentLineAlreadyStarted()) {
            outputWriter.addText(functionDefinition);
            outputWriter.endLine();
        } else {
            outputWriter.printFullLine(functionDefinition);
        }
        outputWriter.raiseIndentLevel();
        outputWriter.startLine();
        outputWriter.addText("return ");
        boolean printResult = getValue().print(outputWriter);
        outputWriter.lowerIndentLevel();
        return printResult;
    }
}
