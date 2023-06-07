package gov.va.sparkcql.dataprovider

import scala.reflect.runtime.universe._
import gov.va.sparkcql.TestBase
import org.hl7.elm.r1.VersionedIdentifier
import gov.va.sparkcql.model.{LibraryData, ValueSetData}
import org.apache.spark.sql.{Dataset, Encoders}

class FileDataProviderTest extends TestBase {
  
  import spark.implicits._

  "A FileDataProvider" should "load and map CQL files" in {    
    val provider = FileDataProvider("./src/test/resources/cql")
    val adapter = provider.createAdapter(spark)
    assert(adapter.read[LibraryData]().filter(_.identifier.id == "BasicRetrieve").count() == 1)
  }

  it should "load and map valuesets" in {
    val provider = FileDataProvider("./src/test/resources/valueset")
    val adapter = provider.createAdapter(spark)
    assert(adapter.read[ValueSetData]().filter(_.name == "Emergency Department Visit").count() == 1)
  }
}