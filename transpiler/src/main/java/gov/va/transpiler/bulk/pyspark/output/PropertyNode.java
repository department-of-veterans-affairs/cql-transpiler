package gov.va.transpiler.bulk.pyspark.output;

public class PropertyNode extends NameValueNode {

    @Override
    public String asOneLine() {
        String value = getValue().asOneLine();
        String name = getName().asOneLine();
        return value == null || name == null ? null : value + "." + name;
    }
    // TODO
}
