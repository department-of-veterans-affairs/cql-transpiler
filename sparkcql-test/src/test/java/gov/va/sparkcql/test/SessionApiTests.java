package gov.va.sparkcql.test;

import static org.junit.Assert.*;

import org.apache.spark.sql.SparkSession;
import org.junit.Test;

import org.hl7.fhir.r4.model.Encounter;

import gov.va.sparkcql.SparkCqlSession;

public class SessionApiTests extends IntegrationTestBase {

    private SparkSession spark = SparkSession.builder()
        .master("local[2]")
        .getOrCreate();

    private SparkCqlSession sparkcql = SparkCqlSession
        .build(spark)
        .withConfig("sparkcql.synthea.size", "PopulationSize10")
        .create();

    @Test
    public void should_retrieve_data_types_directly() {
    }
}