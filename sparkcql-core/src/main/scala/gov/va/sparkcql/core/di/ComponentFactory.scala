package gov.va.sparkcql.core.di

import scala.reflect._
import scala.reflect.runtime.universe._
import scala.collection.JavaConverters._
import java.util.ServiceLoader
import org.apache.spark.sql.SparkSession
import gov.va.sparkcql.core.model._
import gov.va.sparkcql.core.source._
import gov.va.sparkcql.logging.Log

class ComponentFactory {

  def createModels(): List[Model] = {
    val factories = loadFactories[ModelFactory]()
    factories.map(f => {
      f.create()
    })
  }

  def createSources(configurations: List[Configuration], models: List[Model], spark: SparkSession): List[Source] = {
    val factories = loadFactories[SourceFactory]()
    val components = factories.flatMap(f => {
      val searchedCfg = findConfig[SourceConfiguration](configurations, f.configurationType)
      val resolvedCfg = searchedCfg.orElse(f.defaultConfiguration)
      if (resolvedCfg.isDefined) {
        Some(f.create(resolvedCfg, models, spark))
      } else {
        None
      }
    })
    if (components.length == 0) {
      Log.warn(s"No source service providers found on the classpath.")
    }
    components
  }

  protected def loadFactories[T : ClassTag](): List[T] = {
    var cls = classTag[T].runtimeClass
    var factories = ServiceLoader.load(cls).iterator().asScala.toList
    factories.asInstanceOf[List[T]]
  }

  protected def findConfig[C: TypeTag](configurations: List[AnyRef], configurationType: Type): Option[C] = {
    val found = configurations.filter(cfg => {
      val currentType = cfg.getClass()
      currentType.getName() == configurationType.toString()
    })
    assert(found.size <= 1, "Found more than one applicable configurations for SPI component.")
    found.map(_.asInstanceOf[C]).headOption
  }

}
