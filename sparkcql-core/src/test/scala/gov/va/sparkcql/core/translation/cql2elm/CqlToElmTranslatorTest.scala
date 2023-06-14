package gov.va.sparkcql.compiler

import gov.va.sparkcql.TestBase
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertThrows
import java.io.StringWriter
import collection.JavaConverters._
import org.hl7.elm.r1.Library
import org.cqframework.cql.elm.serializing.ElmLibraryWriterFactory
import org.hl7.elm.r1.VersionedIdentifier
import gov.va.sparkcql.core.translation.cql2elm.CqlToElmTranslator
import org.hl7.cql_annotations.r1.CqlToElmError

class CqlToElmTranslatorTest extends TestBase {

  "A CompilerTest" should "translate a single basic CQL to ELM" in {
    val cqlToElm = new CqlToElmTranslator()
    assert(cqlToElm.translate(List("library MyLibrary version '1'")).head.getIdentifier().getId() == "MyLibrary")
  }

  it should "translate multiple dependent modules" in {
    val cqlToElm = new CqlToElmTranslator()
    assert(cqlToElm.translate(List(
      """library MyLibrary version '1'
          using QUICK
          include MyDependency version '2'""",
          "library MyDependency version '2'")).length == 2)
  }

  it should "support multiple anonymous libraries" in {
    val cqlToElm = new CqlToElmTranslator()
    assert(cqlToElm.translate(List("define myconst: 123", "define myconst: 456", "define myconst: 789")).length == 3)
  }

  it should "not support duplicate named libraries with same version" in {
    val cqlToElm = new CqlToElmTranslator()
    assertNoElmErrors(cqlToElm.translate(List("library MyLibrary version '1'", "library MyLibrary version '1'")))
  }

  it should "support duplicate named libraries with differing versions" in {
    val cqlToElm = new CqlToElmTranslator()
    assert(cqlToElm.translate(List("library MyLibrary version '1'", "library MyLibrary version '2'")).length == 2)
  }

  // TODO: Add Provider Scoped scenarios

  it should "successfully translate more complex scripts" in {
    val resourcePaths = Array(
      "cql/BasicRetrieve.cql",
      "cql/LiteralDefinitions.cql"
      )
    val targets = resourcePaths.map(scala.io.Source.fromResource(_).mkString)
    val cqlToElm = new CqlToElmTranslator()
    assertNoElmErrors(cqlToElm.translate(targets.toList))
  }
}