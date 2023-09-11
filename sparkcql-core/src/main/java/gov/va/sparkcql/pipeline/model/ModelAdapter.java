package gov.va.sparkcql.pipeline.model;

import java.io.Serializable;
import java.util.List;

import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.types.StructType;
import org.hl7.elm.r1.ContextDef;

import gov.va.sparkcql.types.DataType;
import scala.Tuple2;

public interface ModelAdapter extends Serializable {

    public String getNamespaceUri();

    public Object deserialize(DataType dataType, String json);

    public String serialize(Object entity);

    public List<Tuple2<DataType, Class<?>>> supportedDataTypes();

    public default void assertDataTypeIsSupported(DataType dataType) {
        if (!isDataTypeSupported(dataType)) {
            throw new RuntimeException("Unsupported data type '" + dataType.toString() + "'.");
        }
    }

    public default Tuple2<DataType, Class<?>> resolveTypeMap(DataType dataType) {
        var r = supportedDataTypes().stream()
                .filter(m -> m._1.equals(dataType))
                .findFirst();

        return r.orElse(null);
    }

    public default Tuple2<DataType, Class<?>> resolveTypeMap(Class<?> clazz) {
        var r = supportedDataTypes().stream()
                .filter(m -> m._2.equals(clazz))
                .findFirst();

        return r.orElse(null);
    }

    public default Boolean isDataTypeSupported(DataType dataType) {
        var m = resolveTypeMap(dataType);
        return m != null;
    }

    public <T> Encoder<T> getEncoder(DataType dataType);

    public StructType getSchema(DataType dataType);

    public String getContextId(Object instance, ContextDef contextDef);
}