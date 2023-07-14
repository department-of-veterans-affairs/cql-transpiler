package gov.va.sparkcql.adapter.model;

import java.util.List;

import javax.xml.namespace.QName;

import org.apache.spark.sql.types.StructType;

import gov.va.sparkcql.adapter.Adapter;

public interface ModelAdapter extends Adapter {
    
    public String namespaceUri();

    public List<QName> supportedDataTypes();
    
    public StructType schemaOf(QName dataType);
    
    default void assertDataTypeSupport(QName dataType) {
        if (!supportedDataTypes().contains(dataType)) {
            throw new RuntimeException("Unsupported data type '" + dataType.toString() + "'.");
        }
    }

    // public QName patientIdentifier();
}