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
import gov.va.sparkcql.di.Components
import gov.va.sparkcql.TestBase
import org.apache.spark.sql.functions._
import gov.va.sparkcql.adapter.model.ModelAdapterFactory
import gov.va.sparkcql.di.ComponentConfiguration

class EvaluatorTest extends TestBase {

  val parameters = {
    val lowDateTime = Converter.convert[DateTime]("2013-01-01")
    val highDateTime = Converter.convert[DateTime]("2014-01-01")
    val interval = new Interval().withLow(lowDateTime).withHigh(highDateTime)
    Map("Measurement Period" -> interval)
  }

  val configuration = new ComponentConfiguration()
  val models = Components.load[ModelAdapterFactory]().map(_.create(configuration))
  val compiler = new Compiler(List())
  val compilation = {
    val compilation = compiler.compile(List(getResource("/cql/ComplexLiteral.cql")))
    writeElm(compilation)
    compilation
  }

  lazy val context = Context(parameters, spark)
  lazy val evaluator = new Evaluator(List(), List(), spark)
  lazy val evaluation = evaluator.evaluate(parameters, compilation)
  lazy val rootNode = new PackageNode(compilation)

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
    //val r = rootNode.evaluate(context)
  }
}