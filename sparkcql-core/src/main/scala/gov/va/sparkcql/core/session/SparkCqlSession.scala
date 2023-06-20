package gov.va.sparkcql.core.session

import scala.reflect.runtime.universe._
import org.apache.spark.sql.{SparkSession, Dataset, Row}
import gov.va.sparkcql.core.translation.cql2elm.CqlToElmTranslator
import gov.va.sparkcql.core.translation.elm2spark.ElmR1ToSparkTranslator
import scala.collection.mutable.MutableList
import gov.va.sparkcql.core.model.VersionedId
import gov.va.sparkcql.core.adapter.source.{SourceAdapterFactory, SourceAdapter}
import gov.va.sparkcql.core.adapter.source.CompositeSourceAdapter
import gov.va.sparkcql.core.adapter.model.{ModelAdapterFactory, ModelAdapter}
import gov.va.sparkcql.core.adapter.model.CompositeModelAdapter
import gov.va.sparkcql.core.model.{Evaluation}
import gov.va.sparkcql.core.adapter.model.NativeModel
import gov.va.sparkcql.core.Log
import javax.xml.namespace.QName

class SparkCqlSession private(builder: SparkCqlSession.Builder) {
  
  lazy val modelAdapters = builder.modelAdapterFactories.map(f => f.create())
  lazy val models = new CompositeModelAdapter(modelAdapters.toList)

  lazy val sourceAdapters = builder.sourceAdapterFactories.map(f => f.create(builder.spark, models))
  lazy val sources = new CompositeSourceAdapter(models, builder.spark, sourceAdapters.toList)

  lazy val cqlToElm = new CqlToElmTranslator(Some(sources))
  lazy val elmToSpark = new ElmR1ToSparkTranslator(Some(sources), Some(models), builder.spark)

  def retrieve[T <: Product : TypeTag](): Option[Dataset[T]] = {
    sources.acquireData[T]()
  }

  def retrieve(dataType: QName): Option[Dataset[Row]] = {
    sources.acquireData(dataType)
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