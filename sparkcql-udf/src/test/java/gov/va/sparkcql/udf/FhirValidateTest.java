package gov.va.sparkcql.udf;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;

public class FhirValidateTest {

    static protected SparkSession getSpark() {
        return SparkSession.builder()
                .master("local[1]")
                .getOrCreate();
    }

    static {
        UdfRegistration.registerFhirValidate(getSpark());
    }

    @DisplayName("Should Confirm a Valid FHIR Instance")
    @ParameterizedTest
    @ValueSource(strings = { "{\"resourceType\": \"Patient\", \"id\": \"123\"}" })
    void validFhirInstances(String instanceJson) {
        var df = getSpark().sql(String.format("select fhir_validate('%s') result", instanceJson));
        assertEquals(df.head().getString(0), "");
    }

    @DisplayName("Should Confirm an Invalid FHIR Instance")
    @ParameterizedTest
    @ValueSource(strings = { "{\"resourceTypo\": \"Patient\", \"id\": \"123\"}", "{\"resourceType\": \"Fake\", \"id\": \"123\"}" })
    void invalidFhirInstances(String instanceJson) {
        var df = getSpark().sql(String.format("select fhir_validate('%s') result", instanceJson));
        assertNotEquals(df.head().getString(0), "");
    }

    @DisplayName("Should Confirm a Large FHIR Bundle")
    @Test
    void validBundle() throws Exception {
        // This time, call it direct instead of through Spark SQL to avoid an extremely large SQL statement.
        var udf = new FhirValidate();
        var bundle = gov.va.sparkcql.common.Resources.load("fhir/Aaron697_Brekke496_2fa15bc7-8866-461a-9000-f739e425860a.json");
        var result = udf.call(bundle);
        assertNotEquals(result, "");
    }
}