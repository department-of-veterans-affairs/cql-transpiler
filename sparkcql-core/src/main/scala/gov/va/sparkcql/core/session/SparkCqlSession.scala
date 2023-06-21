package gov.va.sparkcql.core.session

import scala.reflect.runtime.universe._
import org.apache.spark.sql.{SparkSession, Dataset, Row}
import gov.va.sparkcql.core.translation.cql2elm.CqlToElmTranslator
import gov.va.sparkcql.core.translation.elm2spark.ElmR1ToSparkTranslator
import scala.collection.mutable.MutableList
import gov.va.sparkcql.core.model.VersionedId
import gov.va.sparkcql.core.adapter.{Adapter, AdapterConfig}
import gov.va.sparkcql.core.model.{Evaluation}
import gov.va.sparkcql.core.Log
import javax.xml.namespace.QName
import gov.va.sparkcql.core.adapter.{AdapterServiceLoader, AdapterConfig}
import gov.va.sparkcql.core.adapter.model.{CompositeModelAdapter, ModelAdapterConfig}
import gov.va.sparkcql.core.adapter.source.{CompositeSourceAdapter, SourceAdapterConfig}
import gov.va.sparkcql.core.adapter.data.{CompositeDataAdapter, DataAdapterConfig}

class SparkCqlSession private(modelsAdapters: CompositeModelAdapter, sourceAdapters: CompositeSourceAdapter, dataAdapters: CompositeDataAdapter, spark: SparkSession) {
  
  lazy val cqlToElm = new CqlToElmTranslator(Some(sourceAdapters))
  lazy val elmToSpark = new ElmR1ToSparkTranslator(Some(sourceAdapters), Some(modelsAdapters), spark)

  def retrieve[T <: Product : TypeTag](): Option[Dataset[T]] = {
    sourceAdapters.acquireData[T]()
  }

  def retrieve(dataType: QName): Option[Dataset[Row]] = {
    sourceAdapters.acquireData(dataType)
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

    val adapterConfigs = MutableList[AdapterConfig]()

    def withConfig(adapterConfig: AdapterConfig): Builder = {
      adapterConfigs += adapterConfig
      this
    }

    def withConfig(configPath: String): Builder = {
      ???
      this
    }

    def create(): SparkCqlSession = {

      val loader = new AdapterServiceLoader()

      // TODO: Refactor below to avoid duplicate logic.

      val modelAdapters = loader.modelAdapterFactories.flatMap(f => {
        val cfg = lookupConfig(f.getClass())
        if (cfg.isDefined) {
          Some(f.create(cfg.get))
        } else if (f.isDefaultConfigurable) {
          Some(f.create())
        } else { 
          None 
        }
      })

      val compositeModelAdapters = new CompositeModelAdapter(modelAdapters)

      val sourceAdapters = loader.sourceAdapterFactories.flatMap(f => {
        val cfg = lookupConfig(f.getClass())
        if (cfg.isDefined) {
          Some(f.create(cfg.get, compositeModelAdapters, spark))
        } else if (f.isDefaultConfigurable) {
          Some(f.create(compositeModelAdapters, spark))
        } else {
          None
        }
      })

      val compositeSourceAdapters = new CompositeSourceAdapter(compositeModelAdapters, spark, sourceAdapters)

      val dataAdapters = loader.dataAdapterFactories.flatMap(f => {
        val cfg = lookupConfig(f.getClass())
        if (cfg.isDefined) {
          Some(f.create(cfg.get))
        } else if (f.isDefaultConfigurable) {
          Some(f.create())
        } else {
          None
        }
      })

      val compositeDataAdapters = new CompositeDataAdapter(dataAdapters)

      new SparkCqlSession(compositeModelAdapters, compositeSourceAdapters, compositeDataAdapters, spark)
    }

    protected def lookupConfig[C](adapterFactoryClass: Class[_]): Option[C] = {
      val found = adapterConfigs.filter(cfg => {
        val a = cfg.adapterFactoryType.toString()
        val b = adapterFactoryClass.getName()
        a == b
      })
      assert(found.size <= 1, "Found more than one applicable adapter configurations.")
      found.headOption.asInstanceOf[Option[C]]
    }
  }

  def build(spark: SparkSession): Builder = {
    new Builder(spark)
  }
}