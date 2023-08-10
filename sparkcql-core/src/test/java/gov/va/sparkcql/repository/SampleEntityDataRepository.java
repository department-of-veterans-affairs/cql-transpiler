package gov.va.sparkcql.repository;

import gov.va.sparkcql.model.SampleEntity;

public class SampleEntityDataRepository extends SampleDataRepository<SampleEntity> {

    @Override
    protected String getJsonDataPath() {
        return "sample/sample-data-entity.json";
    }
}