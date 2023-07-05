package gov.va.sparkcql.session

import scala.reflect.runtime.universe._
import scala.collection.mutable.MutableList
import org.apache.spark.sql.{SparkSession, DataFrame, Dataset, Row}
import javax.xml.namespace.QName
import gov.va.sparkcql.compiler.Compiler
import gov.va.sparkcql.logging.Log
import gov.va.sparkcql.di.{Components, ComponentConfiguration}
import gov.va.sparkcql.adapter.library.{LibraryAdapter, LibraryAdapterFactory}
import gov.va.sparkcql.adapter.model.{ModelAdapter, ModelAdapterAggregator, ModelAdapterFactory}
import gov.va.sparkcql.adapter.data.{DataAdapter, DataAdapterAggregator, DataAdapterFactory}
import gov.va.sparkcql.evaluator.Evaluator
import scala.annotation.meta.param
import gov.va.sparkcql.di.Components
import scala.collection.mutable.HashMap
import org.hl7.elm.r1.VersionedIdentifier

class SparkCqlSession private(libraryAdapters: List[LibraryAdapter], modelAdapters: List[ModelAdapter], dataAdapters: List[DataAdapter], spark: SparkSession) {

  val modelAggregate = new ModelAdapterAggregator(modelAdapters)
  val dataAggregate = new DataAdapterAggregator(dataAdapters)

  lazy val compiler = new Compiler(libraryAdapters)
  lazy val evaluator = new Evaluator(modelAdapters, dataAdapters, spark)
  import spark.implicits._

  def retrieve(dataType: QName): Option[DataFrame] = {
    dataAggregate.acquire(dataType)
  }

  def cql[T](cqlText: String): Evaluation = {
    cql(List(cqlText))
  }

  def cql[T](parameters: Map[String, Object], cqlText: String): Evaluation = {
    cql(parameters, List(cqlText))
  }

  def cql[T](cqlText: List[String]): Evaluation = {
    cqlExec(Some(cqlText), None, None)
  }

  def cql[T](parameters: Map[String, Object], cqlText: List[String]): Evaluation = {
    cqlExec(Some(cqlText), None, Some(parameters))
  }

  def cql[T](libraryIdentifiers: Seq[VersionedIdentifier]): Evaluation = {
    cqlExec(None, Some(libraryIdentifiers), None)
  }

  def cql[T](parameters: Map[String, Object], libraryIdentifiers: Seq[VersionedIdentifier]): Evaluation = {
    cqlExec(None, Some(libraryIdentifiers), Some(parameters))
  }

  protected def cqlExec(
      cqlText: Option[List[String]],
      libraryIdentifiers: Option[Seq[VersionedIdentifier]],
      parameters: Option[Map[String, Object]]): Evaluation = {
    
    val params = parameters.getOrElse(Map[String, Object]())
    val compilation = if (cqlText.isDefined) { compiler.compile(cqlText.get) } else { compiler.compile(libraryIdentifiers.get) }
    val translation = evaluator.evaluate(params, compilation)
    Evaluation(compilation, translation)
  }

  protected def convertCompilationToEvaluation(): Evaluation = {
    ???
  }

  def parameter(name: String): ParameterBuilder = new ParameterBuilder(name)
}

object SparkCqlSession {

  class Builder private[SparkCqlSession](val spark: SparkSession) {

    val configurations = new ComponentConfiguration()

    def withConfig(configuration: ComponentConfiguration): Builder = {
      configurations.write(configuration)
      this
    }

    def withConfig(key: String, value: String): Builder = {
      configurations.write(key, value)
      this
    }

    def create(): SparkCqlSession = {
      val libraryAdapters = Components.load[LibraryAdapterFactory]().map(_.create(configurations))
      val modelAdapters = Components.load[ModelAdapterFactory]().map(_.create(configurations))
      val dataAdapters = Components.load[DataAdapterFactory]().map(_.create(configurations, spark))
      new SparkCqlSession(libraryAdapters, modelAdapters, dataAdapters, spark)
    }
  }

  def build(spark: SparkSession): Builder = {
    new Builder(spark)
  }
}