package gov.va.sparkcql.core.session

import scala.reflect.runtime.universe._
import scala.collection.mutable.MutableList
import org.apache.spark.sql.{SparkSession, Dataset, Row}
import javax.xml.namespace.QName
import gov.va.sparkcql.core.translator.cql2elm.CqlToElmTranslator
import gov.va.sparkcql.core.translator.elm2spark.ElmR1ToSparkTranslator
import gov.va.sparkcql.core.types.Identifier
import gov.va.sparkcql.logging.Log
import gov.va.sparkcql.core.di.{ComponentFactory, Configuration}
import gov.va.sparkcql.core.model.{Model, ModelAggregator}
import gov.va.sparkcql.core.source.{Source, SourceAggregator}

class SparkCqlSession private(models: List[Model], sources: List[Source], spark: SparkSession) {

  val modelAggregate = new ModelAggregator(models)
  val sourceAggregate = new SourceAggregator(sources)

  lazy val cqlToElm = new CqlToElmTranslator(sources)
  lazy val elmToSpark = new ElmR1ToSparkTranslator(models, sources, spark)

  def retrieve[T <: Product : TypeTag](): Option[Dataset[T]] = {
    sourceAggregate.acquireData[T]()
  }

  def retrieve(dataType: QName): Option[Dataset[Row]] = {
    sourceAggregate.acquireData(dataType)
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

  def cql[T](libraryIdentifiers: Seq[Identifier]): Evaluation = {
    cqlExec(None, Some(libraryIdentifiers), None)
  }

  def cql[T](parameters: Map[String, Object], libraryIdentifiers: Seq[Identifier]): Evaluation = {
    cqlExec(None, Some(libraryIdentifiers), Some(parameters))
  }

  protected def cqlExec(
      cqlText: Option[List[String]],
      libraryIdentifiers: Option[Seq[Identifier]],
      parameters: Option[Map[String, Object]]): Evaluation = {
    
    val compilation = if (cqlText.isDefined) { cqlToElm.translate(cqlText.get) } else { cqlToElm.translate(libraryIdentifiers.get) }
    val translation = elmToSpark.translate(parameters, compilation)
    Evaluation(compilation, translation)
  }

  protected def convertCompilationToEvaluation(): Evaluation = {
    ???
  }

  def parameter(name: String): ParameterBuilder = new ParameterBuilder(name)
}

object SparkCqlSession {

  class Builder private[SparkCqlSession](val spark: SparkSession) {

    val componentConfigurations = MutableList[Configuration]()

    def withConfig(configuration: Configuration): Builder = {
      componentConfigurations += configuration
      this
    }

    def withConfig(configPath: String): Builder = {
      ???
      this
    }

    def create(): SparkCqlSession = {

      val mainFactory = new ComponentFactory()
      val models = mainFactory.createModels()
      val sources = mainFactory.createSources(componentConfigurations.toList, models, spark)

      new SparkCqlSession(models, sources, spark)
    }
  }

  def build(spark: SparkSession): Builder = {
    new Builder(spark)
  }
}