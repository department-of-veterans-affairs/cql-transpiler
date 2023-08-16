package gov.va.sparkcql.model;

import java.io.Serializable;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.va.sparkcql.entity.DataType;
import gov.va.sparkcql.entity.SampleEntity;
import gov.va.sparkcql.entity.SamplePatient;

public class SampleModel implements ModelAdapter, Serializable {

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
    public Boolean isTypeDefined(DataType dataType) {
        switch (dataType.toString()) {
            case "{http://va.gov/sparkcql/sample}Patient": 
                return true;
        
            case "{http://va.gov/sparkcql/sample}Entity": 
                return true;
            
            default: 
                return false;
        }        
    }
}