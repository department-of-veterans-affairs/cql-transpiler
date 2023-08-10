package gov.va.sparkcql.repository;

import javax.xml.namespace.QName;

public class SampleDataRepositoryFactory implements ClinicalDataRepositoryFactory {

    @Override
    public ClinicalDataRepository<?> create(QName dataType) {
        switch (dataType.toString()) {
            case "{http://gov.va/sparkcql/sample}Patient": return new SamplePatientDataRepository();
            case "{http://gov.va/sparkcql/sample}Entity": return new SampleEntityDataRepository();
            default: return null;
        }
    }
}