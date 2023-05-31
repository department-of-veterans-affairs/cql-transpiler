package gov.va.sparkcql.adapter.library

import org.scalatest.flatspec.AnyFlatSpec
import gov.va.sparkcql.TestBase
import org.hl7.elm.r1.VersionedIdentifier

class FileLibraryDataAdapterTest extends AnyFlatSpec with TestBase {

  "A FileLibraryDataAdapterTest" should "translate a single basic CQL to ELM" in {
    val adapter = new FileLibraryDataAdapter("./src/test/resources/")
    //println(adapter.getLibrarySource(new VersionedIdentifier().withId("BasicRetrieve").withId("1.0")))
  }
}