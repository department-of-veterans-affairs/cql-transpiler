package gov.va.sparkcql

import scala.reflect.runtime.universe._
import scala.collection.mutable.MutableList
import org.apache.spark.sql.{SparkSession, DataFrame, Dataset, Row}
import javax.xml.namespace.QName
import gov.va.sparkcql.compiler.Compiler
import gov.va.sparkcql.io.Log
import gov.va.sparkcql.di.{Components, ComponentConfiguration}
import gov.va.sparkcql.adapter.library.{LibraryAdapter, LibraryAdapterFactory}
import gov.va.sparkcql.adapter.model.{ModelAdapter, ModelAdapterAggregator, ModelAdapterFactory}
import gov.va.sparkcql.adapter.data.{DataAdapter, DataAdapterAggregator, DataAdapterFactory}
import gov.va.sparkcql.translator.{Translator, TranslationPack}
import scala.annotation.meta.param
import gov.va.sparkcql.di.Components
import scala.collection.mutable.HashMap
import org.hl7.elm.r1.VersionedIdentifier
import gov.va.sparkcql.converter.Converter
import org.hl7.elm.r1.{Interval, DateTime, Date, Literal}
import java.time.{LocalDate, LocalDateTime, ZonedDateTime}

class SparkCqlSession private(libraryAdapters: List[LibraryAdapter], modelAdapters: List[ModelAdapter], dataAdapters: List[DataAdapter], spark: SparkSession) {

  val modelAggregate = new ModelAdapterAggregator(modelAdapters)
  val dataAggregate = new DataAdapterAggregator(dataAdapters)

  lazy val compiler = new Compiler(libraryAdapters)
  lazy val translator = new Translator(modelAdapters, dataAdapters, spark)
  import spark.implicits._

  def retrieve(dataType: QName): Option[DataFrame] = {
    dataAggregate.acquire(dataType)
  }

  def cql[T](cqlText: String): TranslationPack = {
    cql(List(cqlText))
  }

  def cql[T](parameters: Map[String, Object], cqlText: String): TranslationPack = {
    cql(parameters, List(cqlText))
  }

  def cql[T](cqlText: List[String]): TranslationPack = {
    cqlExec(Some(cqlText), None, None)
  }

  def cql[T](parameters: Map[String, Object], cqlText: List[String]): TranslationPack = {
    cqlExec(Some(cqlText), None, Some(parameters))
  }

  def cql[T](libraryIdentifiers: Seq[VersionedIdentifier]): TranslationPack = {
    cqlExec(None, Some(libraryIdentifiers), None)
  }

  def cql[T](parameters: Map[String, Object], libraryIdentifiers: Seq[VersionedIdentifier]): TranslationPack = {
    cqlExec(None, Some(libraryIdentifiers), Some(parameters))
  }

  protected def cqlExec(
      cqlText: Option[List[String]],
      libraryIdentifiers: Option[Seq[VersionedIdentifier]],
      parameters: Option[Map[String, Object]]): TranslationPack = {
    
    val params = parameters.getOrElse(Map[String, Object]())
    val compilation = if (cqlText.isDefined) { compiler.compile(cqlText.get) } else { compiler.compile(libraryIdentifiers.get) }
    val translation = translator.translate(params, compilation)
    translation
  }

  protected def convertCompilationToEvaluation(): TranslationPack = {
    ???
  }
}

object SparkCqlSession {

  class SessionBuilder private[SparkCqlSession](val spark: SparkSession) {

    val configurations = new ComponentConfiguration()

    def withConfig(configuration: ComponentConfiguration): SessionBuilder = {
      configurations.write(configuration)
      this
    }

    def withConfig(key: String, value: String): SessionBuilder = {
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

  class ParameterBuilder(name: String) {
    
    def dateTimeInterval(low: String, high: String, lowClosed: Boolean = true, highClosed: Boolean = true): Map[String, Object] = {
      val lowDateTime = Converter.convert[DateTime](low)
      val highDateTime = Converter.convert[DateTime](high)

      val interval = new Interval()
        .withLow(lowDateTime)
        .withLowClosed(lowClosed)
        .withHigh(highDateTime)
        .withHighClosed(highClosed)

      Map(name -> interval)
    }

    def dateInterval(low: String, high: String, lowClosed: Boolean = true, highClosed: Boolean = true): Map[String, Object] = {
      val lowDate = Converter.convert[Date](low)
      val highDate = Converter.convert[Date](high)

      val interval = new Interval()
        .withLow(lowDate)
        .withLowClosed(lowClosed)
        .withHigh(highDate)
        .withHighClosed(highClosed)

      Map(name -> interval)
    }
  }  

  def build(spark: SparkSession): SessionBuilder = {
    new SessionBuilder(spark)
  }

  def buildParameter(name: String): SparkCqlSession.ParameterBuilder = new SparkCqlSession.ParameterBuilder(name)
}