package gov.va.sparkcql.translation.cql2elm

import gov.va.sparkcql.TestBase
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertThrows
import java.io.StringWriter
import collection.JavaConverters._
import org.hl7.elm.r1.Library
import org.cqframework.cql.elm.serializing.ElmLibraryWriterFactory
import gov.va.sparkcql.extensions._
import org.hl7.elm.r1.VersionedIdentifier

class CqlToElmTranslatorTest extends TestBase {

  "A CqlToElmTranslator" should "translate a single basic CQL to ELM" in {
    val translator = new CqlToElmTranslator()
    assert(translator.translate(List("library MyLibrary version '1'")).libraries.head.getIdentifier().getId() == "MyLibrary")
  }

  it should "translate multiple dependent modules" in {
    val translator = new CqlToElmTranslator()
    assert(translator.translate(List(
      """library MyLibrary version '1'
          using QUICK
          include MyDependency version '2'""",
          "library MyDependency version '2'")).libraries.length == 2)
  }

  it should "support multiple anonymous libraries" in {
    val translator = new CqlToElmTranslator()
    assert(translator.translate(List("define myconst: 123", "define myconst: 456", "define myconst: 789")).libraries.length == 3)
  }

  it should "not support duplicate named libraries with same version" in {
    val translator = new CqlToElmTranslator()
    assert(translator.translate(List("library MyLibrary version '1'", "library MyLibrary version '1'")).errors.size > 0)
  }

  it should "support duplicate named libraries with differing versions" in {
    val translator = new CqlToElmTranslator()
    assert(translator.translate(List("library MyLibrary version '1'", "library MyLibrary version '2'")).libraries.length == 2)
  }

  it should "successfully translate more complex scripts" in {
    val resourcePaths = Array(
      "cql/BasicRetrieve.cql",
      "cql/LiteralDefinitions.cql"
      )
    val targets = resourcePaths.map(scala.io.Source.fromResource(_).mkString)
    val translator = new CqlToElmTranslator()
    assert(translator.translate(targets.toList).errors.size == 0)
  }
}