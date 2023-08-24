package gov.va.sparkcql.fixture.sample;

import com.google.inject.Inject;

import gov.va.sparkcql.configuration.SparkFactory;
import gov.va.sparkcql.pipeline.retriever.resolution.TableResolutionStrategy;
import gov.va.sparkcql.types.DataType;

public class SampleDomainClinicalRepository extends SampleClinicalRepository<SampleEntity> {

    @Inject
    public SampleDomainClinicalRepository(SparkFactory sparkFactory, TableResolutionStrategy tableResolutionStrategy) {
        super(sparkFactory, tableResolutionStrategy);
    }

    @Override
    protected String getJsonDataPath() {
        return "sample/sample-data-entity.json";
    }

    @Override
    public DataType getEntityDataType() {
        return new DataType()
            .withNamespaceUri("http://va.gov/sparkcql/sample")
            .withName("Entity");
    }

    @Override
    public Class<SampleEntity> getEntityClass() {
        return SampleEntity.class;
    }
}