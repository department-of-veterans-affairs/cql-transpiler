package gov.va.sparkcql.adapter.source

import scala.reflect.runtime.universe._
import gov.va.sparkcql.TestBase
import org.hl7.elm.r1.VersionedIdentifier
import gov.va.sparkcql.model.providable._
import org.apache.spark.sql.{Dataset, Encoders}
import org.apache.spark.sql.functions._
import gov.va.sparkcql.model.{DataType, ValueSet, CqlContent}
import gov.va.sparkcql.adapter.model.NativeModel

class FileDataProviderTest extends TestBase {
  
  import spark.implicits._
  lazy val model = new NativeModel()
  lazy val source = FileSource("./src/test/resources").create(spark, model)

  "A FileSourceAdapter" should "load and map CQL files (typed)" in {
    assert(source.read[CqlContent]().filter(_.identifier.id == "BasicRetrieve").count() == 1)
  }

  it should "load and map valuesets (typed)" in {
    assert(source.read[ValueSet]().filter(_.name == "Emergency Department Visit").count() == 1)
  }

  it should "load valuesets based on DataType reference" in {
    val dataType = DataType[ValueSet]
    assert(source.read(dataType).filter(col("name").equalTo("Emergency Department Visit")).count() == 1)
  }
}