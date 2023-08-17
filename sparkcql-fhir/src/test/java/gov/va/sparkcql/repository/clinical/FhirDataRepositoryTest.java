package gov.va.sparkcql.repository.clinical;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gov.va.sparkcql.configuration.LocalSparkFactory;
import gov.va.sparkcql.configuration.SparkFactory;

public class FhirDataRepositoryTest {

    private SparkFactory sparkFactory;

    @BeforeEach
    public void setup() {
        this.sparkFactory = new LocalSparkFactory();
    }

    @Test
    public void should_load_synthetic_repository() {
        var repo = new SyntheticFhirEncounterRepository(sparkFactory);
        var ds = repo.acquire();
        assertEquals(7, ds.count());
        assertNotNull(ds.first().getAs("data"));
        assertEquals("{http://hl7.org/fhir}Encounter", ds.first().getAs("dataType"));
    }

    // @Test
    // public void should_avoid_duplicating_synthetic_view() {
    //     var repo1 = new SyntheticFhirEncounterRepository(sparkFactory);
    //     var repo2 = new SyntheticFhirEncounterRepository(sparkFactory);
    // }
}