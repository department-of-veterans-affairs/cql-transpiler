package gov.va.sparkcql.core.session

import scala.reflect.runtime.universe._
import org.apache.spark.sql.{SparkSession, Dataset, Row}
import gov.va.sparkcql.core.translation.cql2elm.CqlToElmTranslator
import gov.va.sparkcql.core.translation.elm2spark.ElmR1ToSparkTranslator
import scala.collection.mutable.MutableList
import gov.va.sparkcql.core.adapter.{Adapter, Factory, Configuration, AdapterLoader, AdapterSet}
import gov.va.sparkcql.core.adapter.model.{CompositeModelAdapter, ModelAdapter}
import gov.va.sparkcql.core.adapter.model.ModelConfiguration

import gov.va.sparkcql.core.adapter.source.{CompositeSourceAdapter, SourceAdapter, SourceConfiguration}
import gov.va.sparkcql.core.model.{Evaluation, VersionedId}
import gov.va.sparkcql.core.Log
import javax.xml.namespace.QName

class SparkCqlSession private(adapterSet: AdapterSet, spark: SparkSession) {

  lazy val cqlToElm = new CqlToElmTranslator(Some(adapterSet.source))
  lazy val elmToSpark = new ElmR1ToSparkTranslator(Some(adapterSet.source), Some(adapterSet.model), spark)

  def retrieve[T <: Product : TypeTag](): Option[Dataset[T]] = {
    adapterSet.source.acquireData[T]()
  }

  def retrieve(dataType: QName): Option[Dataset[Row]] = {
    adapterSet.source.acquireData(dataType)
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

  def cql[T](libraryIdentifiers: Seq[VersionedId]): Evaluation = {
    cqlExec(None, Some(libraryIdentifiers), None)
  }

  def cql[T](parameters: Map[String, Object], libraryIdentifiers: Seq[VersionedId]): Evaluation = {
    cqlExec(None, Some(libraryIdentifiers), Some(parameters))
  }

  protected def cqlExec(
      cqlText: Option[List[String]],
      libraryIdentifiers: Option[Seq[VersionedId]],
      parameters: Option[Map[String, Object]]): Evaluation = {
    
    val compilation = if (cqlText.isDefined) { cqlToElm.translate(cqlText.get) } else { cqlToElm.translate(libraryIdentifiers.get) }
    val evaluation = elmToSpark.translate(parameters, compilation)
    evaluation.asInstanceOf[Evaluation]
  }

  protected def convertCompilationToEvaluation(): Evaluation = {
    ???
  }

  def parameter(name: String): ParameterBuilder = new ParameterBuilder(name)
}

object SparkCqlSession {

  class Builder private[SparkCqlSession](val spark: SparkSession) {

    val adapterConfigs = MutableList[Configuration]()

    def withConfig(adapterConfig: Configuration): Builder = {
      adapterConfigs += adapterConfig
      this
    }

    def withConfig(configPath: String): Builder = {
      ???
      this
    }

    def create(): SparkCqlSession = {

      val loader = new AdapterLoader(spark, adapterConfigs.toList)
      val adapterSet = loader.createAdapters()

      new SparkCqlSession(adapterSet, spark)
    }
  }

  def build(spark: SparkSession): Builder = {
    new Builder(spark)
  }
}