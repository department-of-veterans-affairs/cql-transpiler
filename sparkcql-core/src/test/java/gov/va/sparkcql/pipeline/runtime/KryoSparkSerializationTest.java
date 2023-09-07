package gov.va.sparkcql.pipeline.runtime;

import gov.va.sparkcql.AbstractTest;
import gov.va.sparkcql.domain.EvaluatedContext;
import gov.va.sparkcql.domain.ExpressionReference;
import gov.va.sparkcql.types.QualifiedIdentifier;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.jupiter.api.Test;
import scala.Tuple2;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class KryoSparkSerializationTest extends AbstractTest {

    public static class MockBean {

        private int id;
        private String value;
        private Instant dateTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public Instant getDateTime() {
            return dateTime;
        }

        public void setDateTime(Instant dateTime) {
            this.dateTime = dateTime;
        }
    }

    @Test
    public void should_serialize_mock_df() {
        var a = new MockBean();
        a.setId(1);
        a.setValue("MyValue");
        a.setDateTime(Instant.now());
        var df = spark.createDataFrame(List.of(a), MockBean.class);
        df.show();
    }

    @Test
    public void should_serialize_mock_rdd() {
        var a = new MockBean();
        a.setId(1);
        a.setValue("MyValue");
        a.setDateTime(Instant.now());
        var sc = JavaSparkContext.fromSparkContext(spark.sparkContext());
        var rdd = sc.parallelize(List.of(a));
        var r = rdd.collect();
        var df = spark.createDataFrame(r, MockBean.class);
        df.show();
    }

    @Test
    public void should_serialize_evaluated_context_rdd() {
        List<Tuple2<ExpressionReference, List<Object>>> exprResults = new ArrayList<>();
        exprResults.add(Tuple2.apply(
           new ExpressionReference()
                   .withLibraryIdentifier(new QualifiedIdentifier().withId("mylib"))
                   .withExpressionDefName("MyDef"),
           new ArrayList<>(List.of("foo", "bar", "baz"))
        ));
        var a = new EvaluatedContext()
                .withContextId("12345")
                .withExpressionResults(exprResults);
        var sc = JavaSparkContext.fromSparkContext(spark.sparkContext());
        var rdd = sc.parallelize(List.of(a));
        var r = rdd.collect();
        var df = spark.createDataFrame(r, EvaluatedContext.class);
        df.show();
    }
}