// package gov.va.sparkcql.test;

// import static org.junit.Assert.assertTrue;

// import org.apache.spark.sql.SparkSession;
// import org.junit.Test;

// import gov.va.sparkcql.SparkCqlSession;

// // import gov.va.sparkcql.types._
// // import gov.va.sparkcql.logging.Log
// // import gov.va.sparkcql.session.{SparkCqlSession, Evaluation}
// // import gov.va.sparkcql.synthea.{SyntheaSourceConfiguration, PopulationSize}
// // import gov.va.sparkcql.source.FileSourceConfiguration

// public class PseudoQuickMeasureTests extends IntegrationTestBase {

//     private SparkSession spark = SparkSession.builder()
//         .master("local[2]")
//         .getOrCreate();

//     private SparkCqlSession sparkcql = SparkCqlSession
//         .build(spark)
//         .withConfig("sparkcql.synthea.size", "PopulationSize10")
//         .create();

//     @Test
//     public void should_calc_emergency_department() {
//         assertTrue(true);
//         // var parameter = sparkcql.parameter("Measurement Period").dateTimeInterval("2013-01-01", "2014-01-01")
//         // var evaluation = sparkcql.cql(parameter, Seq(Identifier("ED_QUICK", None, Some("1.0"))))
//         // assertEvaluation(evaluation)
//         // diagnoseEvaluation(evaluation)
//     }
// }