package gov.va.transpiler.sparksql.node.unary;

import static gov.va.transpiler.sparksql.utilities.Standards.SINGLE_VALUE_COLUMN_NAME;

import gov.va.transpiler.sparksql.node.AbstractCQLNode;
import gov.va.transpiler.sparksql.node.Unary;
import gov.va.transpiler.sparksql.node.leaf.AccessModifierNode;
import gov.va.transpiler.sparksql.utilities.CQLNameToSparkSQLName;

public class ExpressionDefNode extends Unary {

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
    public boolean addChild(AbstractCQLNode child) {
        if (child instanceof AccessModifierNode){
            setAccessModifier((AccessModifierNode) child);
            return true;
        }
        return super.addChild(child);
    }

    @Override
    public String asOneLine() {
        String prefix = "CREATE OR REPLACE VIEW " + cqlNameToSparkSQLName.convertNameToSafe(getName()) + " AS (";
        if (getChild().isTable() || getChild().isEncapsulated()) {
            return getChild().asOneLine() == null ? null : prefix + getChild().asOneLine() + ");";
        } else {
            // This protects against statements encapsulating raw literal values
            return getChild().asOneLine() == null ? null : prefix + "SELECT " + getChild().asOneLine() + " " + SINGLE_VALUE_COLUMN_NAME + ");";
        }
    }
}
