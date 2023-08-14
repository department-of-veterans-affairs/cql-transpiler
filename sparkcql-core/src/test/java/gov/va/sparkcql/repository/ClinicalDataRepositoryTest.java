package gov.va.sparkcql.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.spark.sql.Encoders;
import static org.apache.spark.sql.functions.col;
import org.junit.jupiter.api.Test;

import gov.va.sparkcql.entity.SampleEntity;

public class ClinicalDataRepositoryTest {

    @Test
    public void should_read_sample_repository_untyped() {
        var repo = new SampleEntityDataRepository();
        var ds = repo.acquire();
        assertTrue(ds.count() == 4);
        assertTrue(ds.first().getAs("dataType").equals("Entity"));
        assertTrue(ds.first().getAs("primaryCode").equals("ABC"));
    }

    @Test
    public void should_read_sample_repository_typed() {
        var repo = new SampleEntityDataRepository();
        var ds = repo.acquire().select(col("data.*")).as(Encoders.bean(SampleEntity.class));
        assertTrue(ds.first().getName().equals("sample name 1"));
    }
}