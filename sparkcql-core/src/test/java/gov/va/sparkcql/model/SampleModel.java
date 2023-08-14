package gov.va.sparkcql.model;

import javax.xml.namespace.QName;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.va.sparkcql.entity.SampleEntity;
import gov.va.sparkcql.entity.SamplePatient;

public class SampleModel implements Model {

    @Override
    public String getNamespaceUri() {
        return "http://gov.va/sparkcql/sample";
    }

    @Override
    public Object deserialize(QName dataType, String json) {
        try {
            var m = new ObjectMapper();
            switch (dataType.toString().toLowerCase()) {
                case "{http://gov.va/sparkcql/sample}Patient": 
                    return m.readValue(json, SamplePatient.class);
            
                case "{http://gov.va/sparkcql/sample}Entity": 
                    return m.readValue(json, SampleEntity.class);	
                
                default: 
                    return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
