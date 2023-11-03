package gov.va.transpiler.bulk.sparksql.node;

public class PropertyNode extends AbstractNodeOneChild {

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
            return "SELECT col.* FROM (SELECT explode(*) FROM (SELECT _val." + getName() + " FROM (" + getChild().asOneLine() + ")))";
        } else {
            return "SELECT _val." + getName() + " AS _val FROM (" + getChild().asOneLine() + ")";
        }
    }
}
