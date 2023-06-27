package gov.va.sparkcql.source

import scala.reflect.runtime.universe._
import gov.va.sparkcql.TestBase
import org.apache.spark.sql.functions._
import gov.va.sparkcql.types._
import gov.va.sparkcql.model.Model
import gov.va.sparkcql.logging.Log

class FileSourceAdapterTest extends TestBase {
  
  import spark.implicits._
  lazy val models = List[Model]()
  lazy val source = new FileSource(models, spark, "./src/test/resources")

  "A FileSourceAdapter" should "load and map CQL files (typed)" in {
    assert(source.acquireData[IdentifiedText]().get.filter(_.identifier.id == "BasicRetrieve").count() == 1)
    Log.info(source.acquireData[IdentifiedText]().get)
  }

  it should "load and map valuesets (typed)" in {
    assert(source.acquireData[ValueSet]().get.filter(_.name == "Emergency Department Visit").count() == 1)
  }

  it should "load valuesets based on DataType reference" in {
    val dataType = Model.toDataType[ValueSet]()
    assert(source.acquireData(dataType).get.filter(col("name").equalTo("Emergency Department Visit")).count() == 1)
  }

  it should "read empty gracefully" in {
    lazy val emptySource = new FileSource(models, spark, "./src/test/doesnotexist")
    assert(emptySource.acquireData(Model.toDataType[ValueSet]()).isEmpty)
  }
}