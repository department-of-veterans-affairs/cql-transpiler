package gov.va.sparkcql.common.di;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import gov.va.sparkcql.common.configuration.Configuration;
import gov.va.sparkcql.common.spark.SparkFactory;
import gov.va.sparkcql.repository.CqlSourceRepository;

public class DiTest {

    @Test
    public void should_create_one_spark_session_from_factory() {
        var spark = ServiceContext.createOne(SparkFactory.class).create();
        assertNotNull(spark);
    }

    @Test
    public void should_create_many_repositories() {
        var repos = ServiceContext.createMany(CqlSourceRepository.class);
        assertTrue(repos.size() > 0);
    }

    @Test
    public void should_create_using_config() {
        var cfg = new Configuration();
        cfg.writeBinding(Object.class, String.class);
        var dynamic = ServiceContext.createOne(Object.class, cfg);
        assertTrue(dynamic.getClass() == String.class);
    }    
}