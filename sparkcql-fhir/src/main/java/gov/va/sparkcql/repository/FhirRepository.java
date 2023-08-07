package gov.va.sparkcql.repository;

import org.apache.spark.sql.Encoder;
import org.hl7.fhir.r4.model.Resource;

public interface FhirRepository<T extends Resource> extends ClinicalDataRepository<String, T> {
    
    public Encoder<T> getEncoder();
}