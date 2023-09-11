package gov.va.sparkcql.encoder;

import gov.va.sparkcql.AbstractTest;
import gov.va.sparkcql.mock.MockComplexEntity;
import gov.va.sparkcql.mock.MockEntity;
import gov.va.sparkcql.types.DataType;
import gov.va.sparkcql.types.QualifiedIdentifier;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.catalyst.analysis.GetColumnByOrdinal;
import org.apache.spark.sql.catalyst.encoders.ExpressionEncoder;
import org.apache.spark.sql.catalyst.expressions.BoundReference;
import org.apache.spark.sql.catalyst.expressions.UpCast;
import org.apache.spark.sql.catalyst.expressions.objects.Invoke;
import org.apache.spark.sql.catalyst.expressions.objects.StaticInvoke;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.ObjectType;
import org.junit.jupiter.api.Test;
import scala.collection.JavaConverters;
import scala.collection.Seq;
import scala.reflect.ClassTag;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class EncoderCapabilityTest extends AbstractTest {

    private List<MockEntity> makeSimpleTestData() {
        var entities = new ArrayList<MockEntity>();
        for (int i = 0; i < 10; i++) {
            var a = new MockEntity();
            a.setPatientId("ID" + i);
            a.setName("Patient " + i);
            a.setDescription("Desc " + i);
            entities.add(a);
        }
        return entities;
    }

    private List<MockComplexEntity> makeComplexTestData() {
        var entities = new ArrayList<MockComplexEntity>();
        for (int i = 0; i < 10; i++) {
            var a = new MockComplexEntity();
            a.setId(i);
            a.setValue("MyValue" + i);
            a.setDateTime(Instant.now());
            a.setHomogenousList(List.of(
                    new QualifiedIdentifier().withId(String.valueOf(i)).withSystem("namespace"),
                    new QualifiedIdentifier().withId(String.valueOf(i)).withSystem("namespace")
            ));
            a.setHeterogeneousList(List.of(
                    new QualifiedIdentifier().withId(String.valueOf(i)).withSystem("namespace"),
                    new DataType().withName("TheType").withNamespaceUri("namespaceOfType")
            ));
            entities.add(a);
        }

        return entities;
    }

    @Test
    public void should_allow_ds_encoding_with_java_serialization() {
        var encoder = Encoders.javaSerialization(MockComplexEntity.class);
        var ds = spark.createDataset(makeComplexTestData(), encoder);
        var row = ds.toDF().first();
        var r = ds.first();
        assertNotEquals(r.getHeterogeneousList().get(0).getClass(), Object.class);
    }

    @Test
    public void should_allow_ds_encoding_with_bean_serialization() {
        var encoder = Encoders.bean(MockComplexEntity.class);
        var ds = spark.createDataset(makeComplexTestData(), encoder);
        var r = ds.first();
        assertNotEquals(r.getHeterogeneousList().get(0).getClass(), Object.class);
    }

    @Test
    public void should_build_a_primitive_encoder() {
        var data = List.of(1, 2, 3);
        var nativeEncoder = Encoders.INT();
        var nativeExpressionEncoder = (ExpressionEncoder<Integer>)nativeEncoder;
        var nativeSer = nativeExpressionEncoder.objSerializer();
        var nativeDe = nativeExpressionEncoder.objDeserializer();

        var ser = new Invoke(
                new BoundReference(0, ObjectType.apply(Integer.class), true),
                "intValue",
                DataTypes.IntegerType,
                toSeq(),
                null,
                true,
                true,
                true);

        var de = new StaticInvoke(
                Integer.class,
                ObjectType.apply(Integer.class),
                "valueOf",
                toSeq(new UpCast(new GetColumnByOrdinal(0, DataTypes.IntegerType), DataTypes.IntegerType, toSeq("java.lang.Integer"))),
                null,
                true,
                false,
                true);

        var encoder = new ExpressionEncoder<Integer>(ser, de, ClassTag.apply(Integer.class));

        var ds = spark.createDataset(data, nativeEncoder);
        ds.show();
    }

    @SafeVarargs
    private <T> Seq<T> toSeq(T... values) {
        var list = List.of(values);
        return JavaConverters.asScalaIterator(Arrays.stream(values).iterator()).toSeq();
    }

    @Test
    public void should_compose_product_types_dynamically() {
        // TODO: Build an encoder which takes in N encoders and maps them to N fields.
    }

    @Test
    public void should_compose_sum_types_dynamically() {
        // TODO: Build an encoder which takes in N encoders and maps them to a single, heterogeneous list.
    }

    @Test
    public void should_serialize_mock_rdd() {
        // TODO: Build encoder of MockComplexEntity
    }
}