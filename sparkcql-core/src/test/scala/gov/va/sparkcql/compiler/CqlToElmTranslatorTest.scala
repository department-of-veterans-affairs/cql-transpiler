package gov.va.sparkcql.compiler

import gov.va.sparkcql.TestBase
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertThrows
import java.io.StringWriter
import collection.JavaConverters._
import org.hl7.elm.r1.Library
import org.cqframework.cql.elm.serializing.ElmLibraryWriterFactory
import org.hl7.elm.r1.VersionedIdentifier

class CqlCompilerTest extends TestBase {

  "A CqlCompilerTest" should "translate a single basic CQL to ELM" in {
    val compiler = new CqlCompiler()
    assert(compiler.compile(List("library MyLibrary version '1'")).libraries.head.getIdentifier().getId() == "MyLibrary")
  }

  it should "translate multiple dependent modules" in {
    val compiler = new CqlCompiler()
    assert(compiler.compile(List(
      """library MyLibrary version '1'
          using QUICK
          include MyDependency version '2'""",
          "library MyDependency version '2'")).libraries.length == 2)
  }

  it should "support multiple anonymous libraries" in {
    val compiler = new CqlCompiler()
    assert(compiler.compile(List("define myconst: 123", "define myconst: 456", "define myconst: 789")).libraries.length == 3)
  }

  it should "not support duplicate named libraries with same version" in {
    val compiler = new CqlCompiler()
    assert(compiler.compile(List("library MyLibrary version '1'", "library MyLibrary version '1'")).errors.size > 0)
  }

  it should "support duplicate named libraries with differing versions" in {
    val compiler = new CqlCompiler()
    assert(compiler.compile(List("library MyLibrary version '1'", "library MyLibrary version '2'")).libraries.length == 2)
  }

  it should "successfully translate more complex scripts" in {
    val resourcePaths = Array(
      "cql/BasicRetrieve.cql",
      "cql/LiteralDefinitions.cql"
      )
    val targets = resourcePaths.map(scala.io.Source.fromResource(_).mkString)
    val compiler = new CqlCompiler()
    assert(compiler.compile(targets.toList).errors.size == 0)
  }
}