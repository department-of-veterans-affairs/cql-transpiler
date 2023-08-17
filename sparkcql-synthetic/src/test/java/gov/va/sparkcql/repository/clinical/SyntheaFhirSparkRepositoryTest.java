package gov.va.sparkcql.repository.clinical;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gov.va.sparkcql.configuration.LocalSparkFactory;
import gov.va.sparkcql.configuration.SparkFactory;
import gov.va.sparkcql.repository.SyntheaFhirEncounterRepository;
import gov.va.sparkcql.repository.SyntheaTableResolutionStrategy;
import gov.va.sparkcql.repository.SyntheticPopulationSize;

public class SyntheaFhirSparkRepositoryTest {

    private SparkFactory sparkFactory;

    @BeforeEach
    public void setup() {
        this.sparkFactory = new LocalSparkFactory();
    }

    @Test
    public void should_acquire_encounter_data_by_10() {
        var repo = new SyntheaFhirEncounterRepository(sparkFactory, new SyntheaTableResolutionStrategy(), SyntheticPopulationSize.PopulationSize10);
        repo.acquire().show();
    }

//     @Test
//     public void should_load_none_by_default() {
//         var builder = new SyntheaDataAdapterFactory();
//         var adapter = builder.create(new Configuration(), spark);
//         var condition = adapter.acquireData(new QName("http://hl7.org/fhir", "Condition"));
//         assertNull(condition);
//     }    

//     @Test
//     public void should_return_none_for_none_fhir_types() {
//         assertThrows(
//             RuntimeException.class,
//             () -> {
//                 var adapter = new SyntheaSourceAdapter(spark, SyntheticPopulationSize.PopulationSize10);
//                 adapter.acquireData(new QName("http://example.com", "NotFhir"));
//         });
//     }

}