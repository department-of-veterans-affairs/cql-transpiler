package gov.va.sparkcql.repository;

import gov.va.sparkcql.entity.SampleEntity;

public class SamplePatientDataRepository extends SampleDataRepository<SampleEntity> {

    @Override
    protected String getJsonDataPath() {
        return "sample/sample-data-patient.json";
    }
}