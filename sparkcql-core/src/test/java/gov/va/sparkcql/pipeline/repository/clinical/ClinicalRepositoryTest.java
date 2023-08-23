package gov.va.sparkcql.pipeline.repository.clinical;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.spark.sql.Encoders;
import static org.apache.spark.sql.functions.col;
import org.junit.jupiter.api.Test;

import gov.va.sparkcql.AbstractTest;
import gov.va.sparkcql.configuration.LocalSparkFactory;
import gov.va.sparkcql.domain.SampleEntity;

public class ClinicalRepositoryTest extends AbstractTest {

    @Test
    public void should_read_sample_repository_untyped() {
        var repo = new SampleDomainClinicalRepository(new LocalSparkFactory(), null);
        var ds = repo.acquire();
        assertTrue(ds.count() == 4);
        assertTrue(ds.first().getAs("dataType").equals("Entity"));
        assertTrue(ds.first().getAs("primaryCode").equals("ABC"));
    }

    @Test
    public void should_read_sample_repository_typed() {
        var repo = new SampleDomainClinicalRepository(new LocalSparkFactory(), null);
        var ds = repo.acquire().select(col("data.*")).as(Encoders.bean(SampleEntity.class));
        assertTrue(ds.first().getName().equals("sample name 1"));
    }

    // TODO: Should throw error for invalid container schema
}