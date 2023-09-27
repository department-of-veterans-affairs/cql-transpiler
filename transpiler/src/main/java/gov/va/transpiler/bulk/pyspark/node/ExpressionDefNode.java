package gov.va.transpiler.bulk.pyspark.node;


import gov.va.transpiler.bulk.pyspark.OutputWriter;
import gov.va.transpiler.bulk.pyspark.utilities.CQLNameToPythonName;
import gov.va.transpiler.node.OutputNode;
import gov.va.transpiler.node.SingleChildNode;

public class ExpressionDefNode extends SingleChildNode {

    private final CQLNameToPythonName cqlNameToPythonName;

    /** we don't support access modifiers */
    @SuppressWarnings("unused")
    private AccessModifierNode accessModifier;

    public ExpressionDefNode(CQLNameToPythonName cqlNameToPythonName) {
        this.cqlNameToPythonName = cqlNameToPythonName;
    }

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
        String functionDefinition = "def " + cqlNameToPythonName.convertName(getName()) + "(context: CQLModel, data: CQLDataFrame):";
        if (outputWriter.isCurrentLineAlreadyStarted()) {
            outputWriter.addText(functionDefinition);
            outputWriter.endLine();
        } else {
            outputWriter.printFullLine(functionDefinition);
        }
        outputWriter.raiseIndentLevel();
        outputWriter.startLine();
        outputWriter.addText("return ");
        boolean printResult = getChildren().get(0).print(outputWriter);
        outputWriter.lowerIndentLevel();
        return printResult;
    }
}
