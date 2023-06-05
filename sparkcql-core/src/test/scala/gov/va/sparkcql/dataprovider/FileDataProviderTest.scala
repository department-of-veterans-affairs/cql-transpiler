package gov.va.sparkcql.dataprovider

import scala.reflect.runtime.universe._
import gov.va.sparkcql.TestBase
import org.hl7.elm.r1.VersionedIdentifier
import gov.va.sparkcql.model.LibraryData
import org.apache.spark.sql.{Dataset, Encoders}
import gov.va.sparkcql.converter.FileContentToLibraryData

class FileDataProviderTest extends TestBase {
  
  import spark.implicits._

  "A FileDataProvider" should "load and map CQL files" in {    
    val provider = new FileDataProvider("./src/test/resources/cql")
    assert(provider.fetch[LibraryData](spark).filter(_.identifier.id == "BasicRetrieve").count() == 1)

    //var encoder = Encoders.product[LibraryData]
    //var x = provider.fetch[FileContents](spark, typeOf[LibraryData]).get
    //println("HERE!!!!" + x.head().value)
    assert(true)
  }
}