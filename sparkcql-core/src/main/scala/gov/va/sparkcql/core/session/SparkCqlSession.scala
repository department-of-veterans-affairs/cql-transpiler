package gov.va.sparkcql.core.session

import scala.reflect.runtime.universe._
import org.apache.spark.sql.{SparkSession, Dataset, Row}
import gov.va.sparkcql.core.translation.cql2elm.CqlToElmTranslator
import gov.va.sparkcql.core.translation.elm2spark.ElmToSparkTranslator
import scala.collection.mutable.MutableList
import gov.va.sparkcql.core.model.elm.VersionedIdentifier
import gov.va.sparkcql.core.adapter.source.{SourceAdapterFactory, SourceAdapter, SourceComposite}
import gov.va.sparkcql.core.adapter.model.{ModelAdapterFactory, ModelAdapter, ModelComposite}
import gov.va.sparkcql.core.model.DataType
import gov.va.sparkcql.core.adapter.model.NativeModel
import gov.va.sparkcql.core.Log

class SparkCqlSession private(builder: SparkCqlSession.Builder) {
  
  lazy val modelAdapters = builder.modelAdapterFactories.map(f => f.create())
  lazy val models = new ModelComposite().register(modelAdapters.toList)

  lazy val sourceAdapters = builder.sourceAdapterFactories.map(f => f.create(builder.spark, models))
  lazy val sources = new SourceComposite(builder.spark, models).register(sourceAdapters.toList)

  lazy val cqlToElm = new CqlToElmTranslator(Some(sources), Some(builder.spark))
  lazy val elmToSpark = new ElmToSparkTranslator(Some(sources), Some(models), builder.spark)

  def retrieve[T <: Product : TypeTag](): Option[Dataset[T]] = {
    sources.read[T]()
  }

  def retrieve(dataType: DataType): Option[Dataset[Row]] = {
    sources.read(dataType)
  }

  def cql[T](cqlText: String): Evaluation = {
    cql(List(cqlText))
  }

  def cql[T](cqlText: List[String]): Evaluation = {
    cqlExec(Some(cqlText), None, None)
  }

  def cql[T](libraryIdentifiers: Seq[VersionedIdentifier]): Evaluation = {
    cqlExec(None, Some(libraryIdentifiers), None)
  }

  protected def cqlExec(
      cqlText: Option[List[String]],
      libraryIdentifiers: Option[Seq[VersionedIdentifier]],
      parameters: Option[Map[String, Object]]): Evaluation = {
    
    // TODO: Convert Elm agnostic params to Elm types
    val compilation = if (cqlText.isDefined) { cqlToElm.translate(cqlText.get) } else { cqlToElm.translate(libraryIdentifiers.get) }
    val evaluation = elmToSpark.translate(None, compilation)
    evaluation.asInstanceOf[Evaluation]
  }

  protected def convertCompilationToEvaluation(): Evaluation = {
    ???
  }
}

object SparkCqlSession {

  class Builder private[SparkCqlSession](val spark: SparkSession) {

    val sourceAdapterFactories = MutableList[SourceAdapterFactory]()
    val modelAdapterFactories = MutableList[ModelAdapterFactory]()

    def withSource(adapterFactory: SourceAdapterFactory): Builder = {
      sourceAdapterFactories += adapterFactory
      this
    }

    def withModel(adapterFactory: ModelAdapterFactory): Builder = {
      modelAdapterFactories += adapterFactory
      this
    }

    def create(): SparkCqlSession = {
      if (modelAdapterFactories.length == 0) {
        Log.warn("No model adapters specified.")
      }

      modelAdapterFactories += new NativeModel()

      new SparkCqlSession(this)
    }
  }

  def build(spark: SparkSession): Builder = {
    new Builder(spark)
  }
}