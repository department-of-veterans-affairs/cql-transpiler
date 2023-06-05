package gov.va.sparkcql.dataprovider

import scala.reflect.runtime.universe._
import gov.va.sparkcql.TestBase
import org.hl7.elm.r1.VersionedIdentifier
import gov.va.sparkcql.model.IdentifiedLibraryContent
import org.apache.spark.sql.{Dataset, Encoders}
import gov.va.sparkcql.converter.FileContentToIdentifiedLibraryContent

class FileDataProviderTest extends TestBase {
  
  import spark.implicits._

  "A FileDataProvider" should "load and map CQL files" in {    
    val provider = new FileDataProvider("./src/test/resources/cql", new FileContentToIdentifiedLibraryContent())
    assert(provider.fetch[IdentifiedLibraryContent](spark).filter(_.identifier.id == "BasicRetrieve").count() == 1)

    //var encoder = Encoders.product[IdentifiedLibraryContent]
    //var x = provider.fetch[FileContents](spark, typeOf[IdentifiedLibraryContent]).get
    //println("HERE!!!!" + x.head().value)
    assert(true)
  }
}