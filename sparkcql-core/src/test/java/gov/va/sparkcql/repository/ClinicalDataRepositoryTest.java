package gov.va.sparkcql.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.spark.sql.Encoders;
import static org.apache.spark.sql.functions.col;
import org.junit.jupiter.api.Test;

import gov.va.sparkcql.model.SampleEntity;

public class ClinicalDataRepositoryTest {

    @Test
    public void should_read_sample_repository_untyped() {
        var repo = new SampleEntityDataRepository();
        var ds = repo.queryable();
        assertTrue(ds.count() == 3);
        assertTrue(ds.first().getAs("dataType").equals("SampleType"));
        assertTrue(ds.first().getAs("primaryCode").equals("ABC"));
    }

    @Test
    public void should_read_sample_repository_typed() {
        var repo = new SampleEntityDataRepository();
        var ds = repo.queryable().select(col("data.*")).as(Encoders.bean(SampleEntity.class));
        assertTrue(ds.first().getName().equals("sample name 1"));
    }
}