package gov.va.sparkcql.repository.clinical;

import java.util.List;

import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gov.va.sparkcql.configuration.LocalSparkFactory;
import gov.va.sparkcql.configuration.SparkFactory;
import gov.va.sparkcql.configuration.SystemConfiguration;
import gov.va.sparkcql.io.Resources;
import gov.va.sparkcql.repository.resolution.FormulaResolutionStrategy;
import gov.va.sparkcql.repository.resolution.TableResolutionStrategy;

public class FhirDataRepositoryTest implements SparkFactory {

    private TableResolutionStrategy resolutionStrategy;

    @BeforeEach
    public void setup() {
        var cfg = new SystemConfiguration();
        cfg.write("sparkcql.resolutionstrategy.formula", "${domain}");
        this.resolutionStrategy = new FormulaResolutionStrategy(cfg);
    }

    @Override
    public SparkSession create() {
        var spark = new LocalSparkFactory().create();
        registerData(spark, "Encounter");
        return spark;
    }

    private void registerData(SparkSession spark, String dataTypeName) {
        try {
            var json = Resources.read(dataTypeName + ".json");
            var jsonDs = spark.createDataset(List.of(json), Encoders.STRING());
            var rawDs = spark.read().json(jsonDs);
            rawDs.createTempView(dataTypeName.toLowerCase());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void should_acquire_encounter_data() {
        var repo = new FhirEncounterSparkRepository(this, resolutionStrategy);
        repo.acquire().show();
    }
}