package gov.va.sparkcql.translation.cql2elm

import org.scalatest.flatspec.AnyFlatSpec
import gov.va.sparkcql.TestBase
import java.io.StringWriter
import collection.JavaConverters._
import org.hl7.elm.r1.Library
import org.cqframework.cql.elm.serializing.ElmLibraryWriterFactory
import org.cqframework.cql.cql2elm.LibraryContentType
import gov.va.sparkcql.common.Extensions._
import org.hl7.elm.r1.VersionedIdentifier

class CqlToElmBuilderTest extends AnyFlatSpec with TestBase {

  "A CqlElmCompiler" should "translate a single basic CQL to ELM" in {
    val build = new CqlToElmBuilder()
    assert(build.build("library MyLibrary version '1'").head.getIdentifier().getId() == "MyLibrary")
  }

  it should "translate multiple dependent modules" in {
    val build = new CqlToElmBuilder()
    assert(build.build("""library MyLibrary version '1'
                          using QUICK
                          include MyDependency version '2'""",
                          "library MyDependency version '2'").length == 2)
  }

  it should "support multiple anonymous libraries" in {
    val build = new CqlToElmBuilder()
    assert(build.build("define myconst: 123", "define myconst: 456", "define myconst: 789").length == 3)
  }

  it should "not support conflicting named libraries" in {
    val build = new CqlToElmBuilder()
    assertThrows[Exception](build.build("library MyLibrary version '1'", "library MyLibrary version '1'"))
  }

  it should "support duplicate named libraries with differing versions" in {
    val build = new CqlToElmBuilder()
    assert(build.build("library MyLibrary version '1'", "library MyLibrary version '2'").length == 2)
  }

  it should "successfully build more complex scripts" in {
    val resourcePaths = Array(
      "cql/BasicRetrieve.cql",
      "cql/LiteralDefinitions.cql"
      )
    val targets = resourcePaths.map(scala.io.Source.fromResource(_).mkString)
    val build = new CqlToElmBuilder()
    var results = build.build(targets.toArray)    // will throw compilation errors
    results.foreach(library => {
      assert(library.getAnnotation().size() > 0)
    })
  }
}