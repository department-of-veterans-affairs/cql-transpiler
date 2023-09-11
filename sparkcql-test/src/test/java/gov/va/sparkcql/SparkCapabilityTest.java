package gov.va.sparkcql;

import org.apache.spark.api.java.JavaSparkContext;
import org.hl7.fhir.r4.model.Condition;
import org.hl7.fhir.r4.model.Encounter;
import org.hl7.fhir.r4.model.Resource;
import org.junit.jupiter.api.Test;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SparkCapabilityTest extends AbstractTest {

    @Test
    public void should_serde_fhir_dom_across_cluster() {
        var domList = List.of(
                Tuple2.apply("context 1", new Encounter().setId("context 1 encounter")),
                Tuple2.apply("context 2", new Encounter().setId("context 2 encounter")));

        var sc = JavaSparkContext.fromSparkContext(spark.sparkContext());
        var rdd = sc.parallelizePairs(domList);
        var r = rdd.mapPartitions(i -> i).collect();
        assertEquals("context 1 encounter", r.get(0)._2.getId());
        assertEquals("context 2 encounter", r.get(1)._2.getId());
    }

    @Test
    public void should_join_across_feeds_of_dissimilar_types() {
        var feed1 = List.of(
                Tuple2.apply("context 1", new Encounter().setId("context 1 encounter")),
                Tuple2.apply("context 2", new Encounter().setId("context 2 encounter")));

        var feed2 = List.of(
                Tuple2.apply("context 1", new Condition().setId("context 1 condition")),
                Tuple2.apply("context 2", new Condition().setId("context 2 condition")));

        var sc = JavaSparkContext.fromSparkContext(spark.sparkContext());
        var rdd1 = sc.parallelizePairs(feed1);
        var rdd2 = sc.parallelizePairs(feed2);
        var r = rdd1.join(rdd2).collect();
        assertEquals("context 1", r.get(0)._1);
        assertEquals("context 1 encounter", r.get(0)._2._1.getId());
        assertEquals("context 1 condition", r.get(0)._2._2.getId());
        assertEquals("context 2", r.get(1)._1);
        assertEquals("context 2 encounter", r.get(1)._2._1.getId());
        assertEquals("context 2 condition", r.get(1)._2._2.getId());
    }

    @Test
    public void should_be_collectable_into_lists() {
        var feed1 = List.of(
                Tuple2.apply("context 1", List.of(
                    new Encounter().setId("context 1 encounter")
                )),
                Tuple2.apply("context 2", List.of(
                    new Encounter().setId("context 2 encounter")
                )));

        var feed2 = List.of(
                Tuple2.apply("context 1", List.of(
                    new Condition().setId("context 1 condition")
                )),
                Tuple2.apply("context 2", List.of(
                    new Condition().setId("context 2 condition")
                )));

        var sc = JavaSparkContext.fromSparkContext(spark.sparkContext());
        var rdd1 = sc.parallelizePairs(feed1);
        var rdd2 = sc.parallelizePairs(feed2);
        var r = rdd1.join(rdd2).map(v1 -> {
            var combined = new ArrayList<Resource>();
            combined.addAll(v1._2._1);
            combined.addAll(v1._2._2);
            return Tuple2.apply(v1._1, combined);
        }).collect();
        assertEquals(2, r.size());
        assertEquals(2, r.get(0)._2.size());
    }
}