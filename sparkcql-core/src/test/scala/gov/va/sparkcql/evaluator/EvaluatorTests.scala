package gov.va.sparkcql.evaluator

import scala.reflect.runtime.universe._
import collection.JavaConverters._
import gov.va.sparkcql.converter.Converter
import gov.va.sparkcql.types._
import gov.va.sparkcql.logging.Log
import org.hl7.elm.r1.{Interval, DateTime, Date, Literal}
import java.time.{LocalDate, LocalDateTime, ZonedDateTime}
import gov.va.sparkcql.compiler.Compiler
import gov.va.sparkcql.evaluator.node.PackageNode
import gov.va.sparkcql.di.ComponentFactory
import gov.va.sparkcql.TestBase
import org.apache.spark.sql.functions._

class EvaluatorTests extends TestBase {

  lazy val fixture = new {
    val lowDateTime = Converter.convert[DateTime]("2013-01-01")
    val highDateTime = Converter.convert[DateTime]("2014-01-01")
    val interval = new Interval().withLow(lowDateTime).withHigh(highDateTime)
    val parameters = Map("Measurement Period" -> interval)
    val context = Context(parameters, spark)
    val mainFactory = new ComponentFactory()
    val models = mainFactory.createModels()
    val compiler = new Compiler(List())
    val compilation = compiler.compile(List(getResource("/cql/ComplexLiteral.cql")))
    writeElm(compilation)
  }

  lazy val evaluator = new Evaluator(List(), List(), spark)
  lazy val evaluation = evaluator.evaluate(fixture.parameters, fixture.compilation)
  lazy val rootNode = new PackageNode(fixture.compilation)

  "An Evaluator" should "produce descendants of the tree" in {
    assert(rootNode.descendants.length >= 6)
  }

  it should "set the parent for all nodes except root" in {
    assert(rootNode.descendants.filter(_.parent == null).length == 0)
    assert(rootNode.parent == null)
  }

  it should "calculate ancestors up to root" in {
    assert(rootNode.descendants.foldLeft(0)((a, b) => a + b.ancestors.length) > 10)
  }

  it should "correctly evaluate ComplexLiteral using Spark" in {
    val r = rootNode.evaluate(fixture.context)
  }
}