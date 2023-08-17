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

public class FhirDataRepositoryTest {

    private SparkFactory sparkFactory;
    private TableResolutionStrategy resolutionStrategy;

    @BeforeEach
    public void setup() {
        var cfg = new SystemConfiguration();
        cfg.write("sparkcql.resolutionstrategy.formula", "${domain}");
        this.sparkFactory = new LocalSparkFactory();
        this.resolutionStrategy = new FormulaResolutionStrategy(cfg);
    }

    private <T, R extends FhirSparkRepository<T>> void registerData(SparkSession spark, R repo) {
        try {
            var dataType = repo.getEntityDataType();
            var schema = repo.getCanonicalSchema();
            var json = Resources.read(dataType.getName() + ".json");
            var jsonDs = spark.createDataset(List.of(json), Encoders.STRING());
            var rawDs = spark.read().schema(schema).json(jsonDs);
            rawDs.createTempView(dataType.getName().toLowerCase());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void should_acquire_encounter_data() {
        var spark = sparkFactory.create();
        var repo = new FhirEncounterSparkRepository(sparkFactory, resolutionStrategy);
        registerData(spark, repo);
        repo.acquire().show();
    }
}