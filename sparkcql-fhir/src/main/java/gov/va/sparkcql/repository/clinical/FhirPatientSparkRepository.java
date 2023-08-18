package gov.va.sparkcql.repository.clinical;

import com.google.inject.Inject;
import gov.va.sparkcql.configuration.SparkFactory;
import gov.va.sparkcql.repository.resolution.TableResolutionStrategy;
import org.hl7.fhir.r4.model.Patient;

public class FhirPatientSparkRepository extends FhirSparkRepository<Patient> implements FhirPatientClinicalRepository {

    @Inject
    public FhirPatientSparkRepository(SparkFactory sparkFactory, TableResolutionStrategy tableResolutionStrategy) {
        super(sparkFactory, tableResolutionStrategy);
    }

    @Override
    public Class<Patient> getEntityClass() {
        return Patient.class;
    }
}