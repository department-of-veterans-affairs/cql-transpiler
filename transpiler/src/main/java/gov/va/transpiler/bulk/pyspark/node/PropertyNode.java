package gov.va.transpiler.bulk.pyspark.node;

import org.hl7.elm.r1.Expression;

import gov.va.transpiler.bulk.pyspark.utilities.CQLNameToPythonName;
import gov.va.transpiler.node.SingleChildNode;

public class PropertyNode extends SingleChildNode {

    public enum AccessibleType {
        Tuple,
        Function,
        Reference
    }

    private final CQLNameToPythonName cqlNameToPythonName;

    private String scope;
    private Expression source;

    public Expression getSource() {
        return source;
    }

    public void setSource(Expression source) {
        this.source = source;
    }

    public PropertyNode(CQLNameToPythonName cqlNameToPythonName) {
        this.cqlNameToPythonName = cqlNameToPythonName;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public AccessibleType accessibleTypeForCQLSourceType(String sourcetype) {
        if (sourcetype != null) {
            if (sourcetype.startsWith("tuple")) {
                return AccessibleType.Tuple;
            }
        }
        return AccessibleType.Reference;
    }

    @Override
    public String asOneLine() {
        String value;
        AccessibleType accessibleType;
        if (!getChildren().isEmpty()) {
            value = getChildren().get(0).asOneLine();
            accessibleType = accessibleTypeForCQLSourceType(getSource().getResultType().toString());
        } else {
            value = getScope();
            accessibleType = AccessibleType.Reference;
        }

        return value == null || getName() == null ? null :
            value
            + getAccessStartForSourceType(accessibleType)
            + cqlNameToPythonName.convertName(getName())
            + getAccessEndForSourceType(accessibleType);
    }

    protected String getAccessStartForSourceType(AccessibleType sourceType) {
        switch(sourceType) {
            case Function:
                return ".(";
            case Tuple:
                return "['";
            default:
                return ".";
        }
    }

    protected String getAccessEndForSourceType(AccessibleType sourceType) {
        switch(sourceType) {
            case Function:
                return ")";
            case Tuple:
                return "']";
            default:
                return "";
        }
    }
}
