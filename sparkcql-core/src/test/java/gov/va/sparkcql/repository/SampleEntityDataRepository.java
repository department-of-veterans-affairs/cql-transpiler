package gov.va.sparkcql.repository;

import gov.va.sparkcql.entity.SampleEntity;

public class SampleEntityDataRepository extends SampleDataRepository<SampleEntity> {

    @Override
    protected String getJsonDataPath() {
        return "sample/sample-data-entity.json";
    }
}