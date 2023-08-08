package gov.va.sparkcql.common.di;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.va.sparkcql.common.spark.SparkFactory;

public class DiTest {

    @Test
    public void should_create_spark_from_factory() {
        var spark = Components.createOne(SparkFactory.class).create();
        assertNotNull(spark);
    }
}