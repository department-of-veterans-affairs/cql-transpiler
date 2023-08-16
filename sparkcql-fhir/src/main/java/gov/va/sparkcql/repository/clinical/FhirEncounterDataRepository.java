package gov.va.sparkcql.repository.clinical;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.types.StructType;
import org.hl7.fhir.r4.model.Encounter;

import com.google.inject.Inject;

import gov.va.sparkcql.configuration.SparkFactory;
import gov.va.sparkcql.repository.resolution.TableResolutionStrategy;

public class FhirEncounterDataRepository extends FhirDataRepository<Encounter> {

    @Inject
    public FhirEncounterDataRepository(SparkFactory sparkFactory, TableResolutionStrategy tableResolutionStrategy) {
        super(sparkFactory, tableResolutionStrategy);
    }

    @Override
    protected StructType getCanonicalSchema() {
        throw new UnsupportedOperationException("Unimplemented method 'getCanonicalSchema'");
    }

    @Override
    protected Dataset<Row> bind() {
        throw new UnsupportedOperationException("Unimplemented method 'bind'");
    }
    
}
