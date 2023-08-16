package gov.va.sparkcql.repository.clinical;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.spark.sql.Encoders;
import static org.apache.spark.sql.functions.col;
import org.junit.jupiter.api.Test;

import gov.va.sparkcql.AbstractTest;
import gov.va.sparkcql.configuration.LocalSparkFactory;
import gov.va.sparkcql.domain.SampleDomain;
import gov.va.sparkcql.repository.resolution.NoneResolutionStrategy;

public class ClinicalDataRepositoryTest extends AbstractTest {

    @Test
    public void should_read_sample_repository_untyped() {
        var repo = new SampleDomainClinicalRepository(new LocalSparkFactory(), new NoneResolutionStrategy());
        var ds = repo.acquire();
        assertTrue(ds.count() == 4);
        assertTrue(ds.first().getAs("dataType").equals("Entity"));
        assertTrue(ds.first().getAs("primaryCode").equals("ABC"));
    }

    @Test
    public void should_read_sample_repository_typed() {
        var repo = new SampleDomainClinicalRepository(new LocalSparkFactory(), new NoneResolutionStrategy());
        var ds = repo.acquire().select(col("data.*")).as(Encoders.bean(SampleDomain.class));
        assertTrue(ds.first().getName().equals("sample name 1"));
    }
}