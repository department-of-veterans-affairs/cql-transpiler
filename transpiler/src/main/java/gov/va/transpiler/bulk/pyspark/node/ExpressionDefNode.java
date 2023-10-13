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
        String functionDefinition = "def " + cqlNameToPythonName.convertName(getName()) + "(spark: SparkSession, userData: UserProvidedData):";
        if (outputWriter.isCurrentLineAlreadyStarted()) {
            outputWriter.addText(functionDefinition);
            outputWriter.endLine();
        } else {
            outputWriter.printFullLine(functionDefinition);
        }
        outputWriter.raiseIndentLevel();
        outputWriter.startLine();
        boolean printResult;
        if (getChildren().get(0).asOneLine() != null) {
            outputWriter.printFullLine("return " + getChildren().get(0).asOneLine());
            printResult = true;
        } else {
            printResult = getChildren().get(0).print(outputWriter);
            if (printResult) {
                outputWriter.printFullLine("return returnVal");
            }
        }
        outputWriter.lowerIndentLevel();
        return printResult;
    }
}
