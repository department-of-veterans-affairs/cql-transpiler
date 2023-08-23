package gov.va.sparkcql.pipeline.modeladapter;

import java.io.Serializable;
import java.util.List;

import org.apache.spark.sql.Encoder;
import org.hl7.elm.r1.ContextDef;

import gov.va.sparkcql.types.DataType;

public interface ModelAdapter extends Serializable {

    public String getNamespaceUri();

    public Object deserialize(DataType dataType, String json);

    public String serialize(Object entity);

    public List<DataType> supportedDataTypes();

    public default void assertDataTypeIsSupported(DataType dataType) {
        if (!supportedDataTypes().contains(dataType)) {
            throw new RuntimeException("Unsupported data type '" + dataType.toString() + "'.");
        }
    }

    public default Boolean isDataTypeSupported(DataType dataType) {
        if (supportedDataTypes().contains(dataType)) {
            return true;
        } else {
            return false;
        }
    }

    public <T> Encoder<T> getEncoder(DataType dataType);

    public String getContextId(Object instance, ContextDef contextDef);
}