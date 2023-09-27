package gov.va.transpiler.bulk.pyspark.node;

import gov.va.transpiler.bulk.pyspark.utilities.CQLNameToPythonName;
import gov.va.transpiler.node.SingleChildNode;

public class PropertyNode extends SingleChildNode {

    public enum AccessibleType {
        Tuple,
        Function
    }

    private final CQLNameToPythonName cqlNameToPythonName;

    private AccessibleType accessibleType;

    public PropertyNode(CQLNameToPythonName cqlNameToPythonName) {
        this.cqlNameToPythonName = cqlNameToPythonName;
    }

    public AccessibleType getAccessibleType() {
        return accessibleType;
    }

    public void setAccessibleType(AccessibleType accessibleType) {
        this.accessibleType = accessibleType;
    }

    public AccessibleType accessibleTypeForCQLSourceType(String sourcetype) {
        if (sourcetype != null) {
            if (sourcetype.startsWith("tuple")) {
                return AccessibleType.Tuple;
            }
        }
        return null;
    }

    @Override
    public String asOneLine() {
        String value = getChildren().get(0).asOneLine();
        return value == null || getName() == null ? null :
            value
            + getAccessStartForSourceType(getAccessibleType())
            + cqlNameToPythonName.convertName(getName())
            + getAccessEndForSourceType(getAccessibleType());
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
