package gov.va.transpiler.bulk.sparksql.node;


import gov.va.transpiler.node.OutputWriter;
import gov.va.transpiler.bulk.sparksql.utilities.CQLNameToSparkSQLName;
import gov.va.transpiler.node.OutputNode;

public class ExpressionDefNode extends AbstractNodeOneChild {

    private final CQLNameToSparkSQLName cqlNameToSparkSQLName;

    /** we don't support access modifiers */
    @SuppressWarnings("unused")
    private AccessModifierNode accessModifier;

    public ExpressionDefNode(CQLNameToSparkSQLName cqlNameToPythonName) {
        this.cqlNameToSparkSQLName = cqlNameToPythonName;
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
        String prefix = "CREATE OR REPLACE VIEW " + cqlNameToSparkSQLName.convertName(getName()) + " AS (";
        return getChild().asOneLine() == null ? null : prefix + getChild().asOneLine() + ")";
    }

    @Override
    public boolean print(OutputWriter outputWriter) {
        boolean printResult = super.print(outputWriter);
        if (!printResult) {
            String prefix = "CREATE OR REPLACE VIEW " + cqlNameToSparkSQLName.convertName(getName()) + " AS (";
            outputWriter.printFullLine(prefix);
            outputWriter.raiseIndentLevel();
            printResult = getChild().print(outputWriter);
            outputWriter.lowerIndentLevel();
            outputWriter.printFullLine(")");
        }
        return printResult;
    }

    @Override
    public boolean isTable() {
        return getChild().isTable();
    }
}
