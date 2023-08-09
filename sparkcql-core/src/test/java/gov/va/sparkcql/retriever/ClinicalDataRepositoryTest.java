package gov.va.sparkcql.retriever;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.spark.sql.Encoders;
import static org.apache.spark.sql.functions.col;
import org.junit.jupiter.api.Test;

public class ClinicalDataRepositoryTest {

    @Test
    public void should_read_sample_repository() {
        var repo = new SampleClinicalDataRepository();
        var ds = repo.acquireQueryable(SampleData.class);
        assertTrue(ds.count() == 3);
        assertTrue(ds.first().getAs("dataType").equals("SampleType"));
        assertTrue(ds.first().getAs("primaryCode").equals("ABC"));
        var typedDs = ds.select(col("data.*")).as(Encoders.bean(SampleData.class));
        assertTrue(typedDs.first().getName().equals("sample name 1"));
    }
}