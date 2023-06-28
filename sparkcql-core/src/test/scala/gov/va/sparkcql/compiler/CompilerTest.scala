package gov.va.sparkcql.compiler

import gov.va.sparkcql.TestBase
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertThrows
import java.io.StringWriter
import collection.JavaConverters._
import org.hl7.elm.r1.Library
import org.cqframework.cql.elm.serializing.ElmLibraryWriterFactory
import org.hl7.elm.r1.VersionedIdentifier
import org.hl7.cql_annotations.r1.CqlToElmError

class CompilerTest extends TestBase {

  "A CompilerTest" should "translate a single basic CQL to ELM" in {
    val compiler = new Compiler()
    assert(compiler.compile(List("library MyLibrary version '1'")).head.getIdentifier().getId() == "MyLibrary")
  }

  it should "translate multiple dependent modules" in {
    val compiler = new Compiler()
    assert(compiler.compile(List(
      """library MyLibrary version '1'
          using QUICK
          include MyDependency version '2'""",
          "library MyDependency version '2'")).length == 2)
  }

  it should "support multiple anonymous libraries" in {
    val compiler = new Compiler()
    assert(compiler.compile(List("define myconst: 123", "define myconst: 456", "define myconst: 789")).length == 3)
  }

  it should "not support duplicate named libraries with same version" in {
    val compiler = new Compiler()
    assertNoElmErrors(compiler.compile(List("library MyLibrary version '1'", "library MyLibrary version '1'")))
  }

  it should "support duplicate named libraries with differing versions" in {
    val compiler = new Compiler()
    assert(compiler.compile(List("library MyLibrary version '1'", "library MyLibrary version '2'")).length == 2)
  }

  // TODO: Add Provider Scoped scenarios

  // TODO: Fail on duplicate library

  def assertNoElmErrors(libraries: Seq[Library]): Unit = {
    val errors = libraries.flatMap(_.getAnnotation().asScala).filter {
      case e: CqlToElmError => true
      case _ => false
    }
    val x = errors.length
    assert(errors.length == 0, errors.headOption.getOrElse(""))
  }  
}