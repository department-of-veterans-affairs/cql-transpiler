package gov.va.sparkcql.core.adapter.source

import scala.reflect.runtime.universe._
import gov.va.sparkcql.TestBase
import org.hl7.elm.r1.VersionedIdentifier
import org.apache.spark.sql.{Dataset, Encoders}
import org.apache.spark.sql.functions._
import gov.va.sparkcql.core.model.{DataType, ValueSet, CqlContent}
import gov.va.sparkcql.core.adapter.model.{CompositeModelAdapter}
import gov.va.sparkcql.core.adapter.model.CanonicalModelAdapter

class FileSourceAdapterTest extends TestBase {
  
  import spark.implicits._
  lazy val model = new CompositeModelAdapter(new CanonicalModelAdapter())
  lazy val source = new FileSourceAdapter(model, spark, "./src/test/resources")

  "A FileSourceAdapter" should "load and map CQL files (typed)" in {
    assert(source.acquireData[CqlContent]().get.filter(_.identifier.id == "BasicRetrieve").count() == 1)
  }

  it should "load and map valuesets (typed)" in {
    assert(source.acquireData[ValueSet]().get.filter(_.name == "Emergency Department Visit").count() == 1)
  }

  it should "load valuesets based on DataType reference" in {
    val dataType = DataType[ValueSet]()
    assert(source.acquireData(dataType).get.filter(col("name").equalTo("Emergency Department Visit")).count() == 1)
  }

  it should "read empty gracefully" in {
    lazy val emptySource = new FileSourceAdapter(model, spark, "./src/test/doesnotexist")
    assert(emptySource.acquireData(DataType[ValueSet]()).isEmpty)
  }
}