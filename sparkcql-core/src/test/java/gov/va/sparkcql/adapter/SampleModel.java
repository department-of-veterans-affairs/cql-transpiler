package gov.va.sparkcql.adapter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.va.sparkcql.domain.SampleEntity;
import gov.va.sparkcql.domain.SamplePatient;
import gov.va.sparkcql.pipeline.modeladapter.ModelAdapter;
import gov.va.sparkcql.types.DataType;

public class SampleModel implements ModelAdapter {

    @Override
    public String getNamespaceUri() {
        return "http://va.gov/sparkcql/sample";
    }

    @Override
    public Object deserialize(DataType dataType, String json) {
        try {
            var m = new ObjectMapper();
            switch (dataType.toString()) {
                case "{http://va.gov/sparkcql/sample}Patient": 
                    return m.readValue(json, SamplePatient.class);
            
                case "{http://va.gov/sparkcql/sample}Entity": 
                    return m.readValue(json, SampleEntity.class);	
                
                default: 
                    return null;
            }
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
    public List<DataType> supportedDataTypes() {
        return List.of(
            new DataType(getNamespaceUri(), "SampleEntity"),
            new DataType(getNamespaceUri(), "SamplePatient")
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
}