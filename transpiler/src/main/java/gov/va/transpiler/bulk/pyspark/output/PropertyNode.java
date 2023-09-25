package gov.va.transpiler.bulk.pyspark.output;


public class PropertyNode extends NameValueNode {

    public enum AccessibleType {
        Tuple,
        Function
    }

    private AccessibleType accessibleType;

    public AccessibleType getAccessibleType() {
        return accessibleType;
    }

    public void setAccessibleType(AccessibleType accessibleType) {
        this.accessibleType = accessibleType;
    }

    public AccessibleType accessibleTypeForCQLSourceType(String sourcetype) {
        if (sourcetype.startsWith("tuple")) {
            return AccessibleType.Tuple;
        }
        return null;
    }

    @Override
    public String asOneLine() {
        String value = getValue().asOneLine();
        String name = getName().asOneLine();
        return value == null || name == null ? null :
         value + getAccessStartForSourceType(getAccessibleType()) + name + getAccessEndForSourceType(getAccessibleType());
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
