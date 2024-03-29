package gov.va.transpiler.sparksql.node.unary;

import static gov.va.transpiler.sparksql.utilities.Standards.SINGLE_VALUE_COLUMN_NAME;

import gov.va.transpiler.sparksql.node.Unary;

public class PropertyNode extends Unary {

    private String scope;

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public boolean isColumnReference() {
        return getScope() != null || getChild().isColumnReference();
    }

    @Override
    public String asOneLine() {
        if (isColumnReference()) {
            if (getScope() != null) {
                return getScope() + "." + getName();
            } else {
                return getChild().asOneLine() + "." + getName();
            }
        } else if (getChild().isTable()) {
            // decompress compressed child tables
            return "SELECT col.* FROM (SELECT explode(*) FROM (SELECT " + SINGLE_VALUE_COLUMN_NAME + "." + getName() + " FROM (" + getChild().asOneLine() + ")))";
        } else {
            // Any child that can be referenced by property must be stored as a single-value table with a _val column
            return "SELECT " + SINGLE_VALUE_COLUMN_NAME + "." + getName() + " AS " + SINGLE_VALUE_COLUMN_NAME + " FROM (" + getChild().asOneLine() + ")";
        }
    }

    @Override
    public boolean isEncapsulated() {
        return !(isColumnReference() || getChild().isTable());
    }
}
