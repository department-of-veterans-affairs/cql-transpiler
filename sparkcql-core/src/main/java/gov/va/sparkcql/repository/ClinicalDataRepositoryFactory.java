package gov.va.sparkcql.repository;

import javax.xml.namespace.QName;

public interface ClinicalDataRepositoryFactory {
    
    ClinicalDataRepository<?> create(QName dataType);
}