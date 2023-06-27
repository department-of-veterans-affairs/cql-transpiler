package gov.va.sparkcql.translator.elm2spark

import gov.va.sparkcql.converter.Converter._
import scala.reflect.runtime.universe._
import collection.JavaConverters._
import gov.va.sparkcql.types._
import gov.va.sparkcql.logging.Log
import org.hl7.elm.r1.{Interval, DateTime, Date, Literal}
import java.time.{LocalDate, LocalDateTime, ZonedDateTime}
import gov.va.sparkcql.translator.cql2elm.CqlToElmTranslator
import gov.va.sparkcql.translator.elm2spark.evaluator.{Context, PackageEvaluator}
import gov.va.sparkcql.di.ComponentFactory
import gov.va.sparkcql.TestBase

class EvaluatorTests extends TestBase {

  lazy val fixture = new {
    val mainFactory = new ComponentFactory()
    val models = mainFactory.createModels()
    val cqlToElm = new CqlToElmTranslator(List())
    val compilation = cqlToElm.translate(List(getResource("/cql/ComplexLiteral.cql")))
    val evaluator = new PackageEvaluator(compilation)
    writeElm(compilation)

    val lowDateTime = convert[String, DateTime]("2013-01-01")
    val highDateTime = convert[String, DateTime]("2014-01-01")
    val interval = new Interval().withLow(lowDateTime).withHigh(highDateTime)
    val params = Map("Measurement Period" -> interval)
    lazy val context = Context(spark, params)
  }

  "An Evaluator" should "produce descendants of the tree" in {
    assert(fixture.evaluator.descendants.length >= 6)
  }

  it should "set the parent for all nodes except root" in {
    assert(fixture.evaluator.descendants.filter(_.parent == null).length == 0)
    assert(fixture.evaluator.parent == null)
  }

  it should "calculate ancestors up to root" in {
    assert(fixture.evaluator.descendants.foldLeft(0)((a, b) => a + b.ancestors.length) > 10)
  }

  it should "correctly evaluate ComplexLiteral using Spark" in {
    val r = fixture.evaluator.evaluate(fixture.context)
  }
}