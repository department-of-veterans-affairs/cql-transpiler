package gov.va.sparkcql.repository;

import java.util.List;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.hl7.fhir.r4.model.Encounter;

import gov.va.sparkcql.entity.ClinicalDataTable;

public class FhirEncounterSparkRepository extends FhirSparkRepository<Encounter> implements FhirEncounterRepository {

    @Override
    protected Class<Encounter> getEntityClass() {
        return Encounter.class;
    }

    @Override
    public Encoder<Encounter> getEncoder() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEncoder'");
    }

    @Override
    public ClinicalDataTable<Encounter> findOne(String key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findOne'");
    }

    @Override
    public List<ClinicalDataTable<Encounter>> findMany(List<String> keys) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findMany'");
    }

    @Override
    public Boolean exists(String key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'exists'");
    }
}