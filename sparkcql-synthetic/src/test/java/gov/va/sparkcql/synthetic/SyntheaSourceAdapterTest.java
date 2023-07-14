package gov.va.sparkcql.synthetic;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.xml.namespace.QName;

import org.junit.jupiter.api.Test;

import gov.va.sparkcql.Configuration;

import org.apache.spark.sql.SparkSession;
import java.lang.RuntimeException;

public class SyntheaSourceAdapterTest {

    SparkSession spark;
    SyntheaSourceAdapter adapter;

    public SyntheaSourceAdapterTest() {
        this.spark = SparkSession.builder()
            .master("local[1]")
            .getOrCreate();
    }

    @Test
    public void should_dynamically_retrieve_resources() {
        var adapter = new SyntheaSourceAdapter(spark, PopulationSize.PopulationSize10);
        var condition = adapter.acquireData(new QName("http://hl7.org/fhir", "Condition"));
        assertNotNull(condition.head().getAs("id"));
        var encounter = adapter.acquireData(new QName("http://hl7.org/fhir", "Encounter"));
        assertNotNull(encounter.head().getAs("status"));
    }

    @Test
    public void should_load_none_by_default() {
        var builder = new SyntheaDataAdapterFactory();
        var adapter = builder.create(new Configuration(), spark);
        var condition = adapter.acquireData(new QName("http://hl7.org/fhir", "Condition"));
        assertNull(condition);
    }    

    @Test
    public void should_return_none_for_none_fhir_types() {
        assertThrows(
            RuntimeException.class,
            () -> {
                var adapter = new SyntheaSourceAdapter(spark, PopulationSize.PopulationSize10);
                adapter.acquireData(new QName("http://example.com", "NotFhir"));
        });
    }
}