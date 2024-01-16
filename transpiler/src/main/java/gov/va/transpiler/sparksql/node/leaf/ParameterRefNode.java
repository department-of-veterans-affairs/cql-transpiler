package gov.va.transpiler.sparksql.node.leaf;

import gov.va.transpiler.sparksql.node.Leaf;
import gov.va.transpiler.sparksql.utilities.CQLNameToSparkSQLName;

public class ParameterRefNode extends Leaf {

    private final CQLNameToSparkSQLName cqlNameToSparkSQLName;

    public ParameterRefNode(CQLNameToSparkSQLName cqlNameToPythonName) {
        this.cqlNameToSparkSQLName = cqlNameToPythonName;
    }
    @Override
    public String asOneLine() {
        return "@" + cqlNameToSparkSQLName.convertNameToSafe(getName());
    }
}
