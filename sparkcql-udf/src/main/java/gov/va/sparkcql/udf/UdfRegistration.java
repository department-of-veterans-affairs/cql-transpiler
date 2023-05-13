package gov.va.sparkcql.udf;

import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;

final class UdfRegistration {
    private UdfRegistration() {}

    public static void registerAll(SparkSession spark) {
        registerFhirValidate(spark);
    }

    public static void registerFhirValidate(SparkSession spark) {
        spark.sqlContext().udf().register("fhir_validate", new FhirValidate(), DataTypes.StringType);
    }
}