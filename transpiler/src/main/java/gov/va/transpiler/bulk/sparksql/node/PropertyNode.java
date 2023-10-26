package gov.va.transpiler.bulk.sparksql.node;

import org.hl7.cql.model.DataType;
import org.hl7.cql.model.ListType;
import org.hl7.cql.model.ClassType;

public class PropertyNode extends AbstractNodeOneChild {

    private DataType resultType;
    private String scope;

    public DataType getResultType() {
        return resultType;
    }

    public void setResultType(DataType resultType) {
        this.resultType = resultType;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public boolean isChildTable() {
        // a table is a list of classes
        return hasChild() && (getChild().isTable() || (getResultType() instanceof ListType && ((ListType) getResultType()).getElementType() instanceof ClassType));
    }

    @Override
    public String asOneLine() {
        if (!hasChild()) {
            // we're in the middle of a retrieve
            return getScope() + "." + getName();
        } if (isChildTable()) {
            // decompress compressed child tables
            return "SELECT col.* FROM (SELECT explode(*) FROM (SELECT _val." + getName() + " FROM (" + getChild().asOneLine() + ")))";
        }
        return "SELECT _val." + getName() + " AS _val FROM (" + getChild().asOneLine() + ")";
    }
}
