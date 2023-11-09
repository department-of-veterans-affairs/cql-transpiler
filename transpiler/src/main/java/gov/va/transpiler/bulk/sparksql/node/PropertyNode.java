package gov.va.transpiler.bulk.sparksql.node;

import static gov.va.transpiler.bulk.sparksql.utilities.Standards.SINGLE_VALUE_COLUMN_NAME;

import org.hl7.elm.r1.Property;

public class PropertyNode extends AbstractNodeOneChild<Property> {

    private String scope;

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public boolean isColumnReference() {
        return (getScope() != null) || (getChild() instanceof PropertyNode && ((PropertyNode) getChild()).isColumnReference());
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
            return "SELECT " + SINGLE_VALUE_COLUMN_NAME + "." + getName() + " AS " + SINGLE_VALUE_COLUMN_NAME + " FROM (" + getChild().asOneLine() + ")";
        }
    }
}
