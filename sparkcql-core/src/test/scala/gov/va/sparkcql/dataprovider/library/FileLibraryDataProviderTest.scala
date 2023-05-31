package gov.va.sparkcql.dataprovider.library

import gov.va.sparkcql.TestBase
import org.hl7.elm.r1.VersionedIdentifier

class FileLibraryDataProviderTest extends TestBase {

  "A FileLibraryDataProviderTest" should "translate a single basic CQL to ELM" in {
    val adapter = new FileLibraryDataProvider("./src/test/resources/")
    //println(adapter.getLibrarySource(new VersionedIdentifier().withId("BasicRetrieve").withId("1.0")))
  }
}