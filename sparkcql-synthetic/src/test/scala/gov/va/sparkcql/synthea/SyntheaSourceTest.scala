package gov.va.sparkcql.synthea

import gov.va.sparkcql.TestBase
import org.apache.spark.sql.{SparkSession, DataFrame}
import org.apache.spark.sql.functions._
import org.hl7.elm.r1.Code
import gov.va.sparkcql.session.SparkCqlSession
import gov.va.sparkcql.fhir.FhirDataType
import gov.va.sparkcql.fhir.FhirModelAdapter
import gov.va.sparkcql.di.ComponentConfiguration
import gov.va.sparkcql.adapter.model.ModelAdapter

class SyntheaSourceTest extends TestBase {

  "A SyntheaSource" should "dynamically retrieve resources" in {
    val source = new SyntheaDataAdapter(spark, SampleSize.SampleSize10)
    assert(source.acquire(FhirDataType("Encounter")).get.head().getAs[String]("status") != null)
    assert(source.acquire(FhirDataType("Condition")).get.head().getAs[String]("id") != null)
  }

  it should "load none by default" in {
    val sourceFactory = new SyntheaDataAdapterFactory()
    val sourceDefaultConfig = sourceFactory.create(sourceFactory.defaultConfiguration.get, spark)
    assert(sourceDefaultConfig.acquire(FhirDataType("Encounter")).isEmpty)
    val sourceEmptyConfig = sourceFactory.create(new ComponentConfiguration(), spark)
    assert(sourceEmptyConfig.acquire(FhirDataType("Encounter")).isEmpty)
  }

  it should "return exactly 10 bundles when using SampleSize10" in {
    val source = new SyntheaDataAdapter(spark, SampleSize.SampleSize10)
    assert(source.acquire(FhirDataType("Patient")).get.distinct().count() == 10)
  }

  // TODO
  // it should "return exactly 1000 bundles when using Size1000" in {
  //   val source = new SyntheaSource(spark, Size.Size10)
  //   assert(source.acquire(FhirDataType("Patient")).get.distinct().count() == 10)
  // }

  it should "return None for non-FHIR data types" in {
    val source = new SyntheaDataAdapter(spark, SampleSize.SampleSize10)
    assert(source.acquire(ModelAdapter.toDataType("http://example.com", "NotFhir")).isEmpty)
  }
}