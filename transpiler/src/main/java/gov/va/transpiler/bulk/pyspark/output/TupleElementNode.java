package gov.va.transpiler.bulk.pyspark.output;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class TupleElementNode extends NameValueNode {

    public enum PY_SPARK_DATA_TYPE {
        StringType,
        LongType
    }

    private static final HashMap<String, PY_SPARK_DATA_TYPE> dataTypeMappings = new LinkedHashMap<>();

    {
        // populate dateTypeMappings
        dataTypeMappings.put("System.Integer", PY_SPARK_DATA_TYPE.LongType);
        dataTypeMappings.put("System.String", PY_SPARK_DATA_TYPE.StringType);
    }

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private PY_SPARK_DATA_TYPE cqlTypeToPySparkType(String type) {
        return dataTypeMappings.get(type);
    }

    @Override
    public String asOneLine() {
        return "StructField(" + getValue().asOneLine() + ", " + cqlTypeToPySparkType(getType()) + "(), True)";
    }
}
