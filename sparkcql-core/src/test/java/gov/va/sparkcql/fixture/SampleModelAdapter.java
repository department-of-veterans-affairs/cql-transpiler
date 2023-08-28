package gov.va.sparkcql.fixture;

import java.util.List;

import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.hl7.elm.r1.ContextDef;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.va.sparkcql.pipeline.model.ModelAdapter;
import gov.va.sparkcql.types.DataType;
import scala.Tuple2;

public class SampleModelAdapter implements ModelAdapter {

    @Override
    public String getNamespaceUri() {
        return "http://va.gov/sparkcql/sample";
    }

    @Override
    public Object deserialize(DataType dataType, String json) {
        try {
            var m = new ObjectMapper();
            var typeMap = this.resolveTypeMap(dataType);
            return m.readValue(json, typeMap._2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String serialize(Object entity) {
        try {
            var m = new ObjectMapper();
            return m.writeValueAsString(entity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Tuple2<DataType, Class<?>>> supportedDataTypes() {
        return List.of(
            Tuple2.apply(new DataType(getNamespaceUri(), "SampleEntity"), SampleEntity.class),
            Tuple2.apply(new DataType(getNamespaceUri(), "SamplePatient"), SamplePatient.class)
        );
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Encoder<T> getEncoder(DataType dataType) {
        assertDataTypeIsSupported(dataType);
        switch (dataType.toString()) {
            case "{http://va.gov/sparkcql/sample}SampleEntity": 
                return (Encoder<T>)Encoders.bean(SampleEntity.class);

            case "{http://va.gov/sparkcql/sample}SamplePatient": 
                return (Encoder<T>)Encoders.bean(SamplePatient.class);
                
            default:
                throw new RuntimeException("Unexpected data type '" + dataType.toString() + "'.");
        }
    }

    @Override
    public String getContextId(Object instance, ContextDef contextDef) {
        if (instance instanceof SamplePatient) {
            return ((SamplePatient)instance).getId();
        } else if (instance instanceof SampleEntity) {
            return ((SampleEntity)instance).getPatientId();
        } else {
            throw new RuntimeException(
                "Unable to resolve context ID for unknown type " + instance.getClass().getSimpleName());
        }
    }
}