package gov.va.sparkcql.core.adapter

import scala.reflect.runtime.universe._
import scala.reflect._
import scala.collection.JavaConverters._
import java.util.ServiceLoader
import gov.va.sparkcql.core.Log
import scala.reflect.ClassTag
import org.apache.spark.sql.SparkSession
import gov.va.sparkcql.core.adapter.source.{SourceAdapter, CompositeSourceAdapter, SourceConfiguration, SourceFactory}
import gov.va.sparkcql.core.adapter.model.{ModelAdapter, CompositeModelAdapter, ModelFactory}
import gov.va.sparkcql.core.adapter.model.ModelConfiguration

case class LoadedAdapters()

class AdapterLoader(spark: SparkSession, configs: List[Configuration]) {

  protected lazy val adapterFactories: List[Factory] = {
    var cls = classTag[Factory].runtimeClass
    var factories = ServiceLoader.load(cls).iterator().asScala.toList
    if (factories.length == 0) {
      Log.warn(s"No adapter service providers found on the classpath.")
    }
    factories.asInstanceOf[List[Factory]]
  }

  protected def findConfig(adapterFactory: Factory): Option[Configuration] = {
    val found = configs.filter(cfg => {
      val a = cfg.adapterFactoryType.toString()
      val b = adapterFactory.getClass().getName()
      a == b
    })
    assert(found.size <= 1, "Found more than one applicable adapter configurations.")
    found.headOption
  }

  protected def createForFactoryType[F <: Factory : TypeTag, A, C](instantiate: (F, Option[C]) => Adapter): List[A] = {
    // TODO: Clean up code below. Works but certaintly there's a better, or at least more documented, way.
    val factories = adapterFactories.filter(f => {
      val check = f.getClass().getGenericInterfaces().filter(_.getTypeName() == typeOf[F].toString())
      check.length > 0
    })
    factories.flatMap(f => {
      val factory = f.asInstanceOf[F]
      val cfg = findConfig(f)
      if (cfg.isDefined) {
        val castCfg = cfg.map(_.asInstanceOf[C])
        Some(instantiate(factory, castCfg).asInstanceOf[A])
      } else if (f.isDefaultConfigurable) {
        if (f.defaultConfiguration.isEmpty) {
          throw new Exception("No default configuration for default configurable adapter.")
        }
        val castCfg = f.defaultConfiguration.map(_.asInstanceOf[C])
        Some(instantiate(factory, castCfg).asInstanceOf[A])
      } else {
        None
      }
    })
  }

  def createAdapters(): AdapterSet = {
    val modelAdapters = createForFactoryType[ModelFactory, ModelAdapter, ModelConfiguration](
      (f, c) => f.create(c))
    val modelComposite = new CompositeModelAdapter(modelAdapters)

    val sourceAdapters = createForFactoryType[SourceFactory, SourceAdapter, SourceConfiguration](
      (f, c) => f.create(c, modelComposite, spark))
    val sourceComposite = new CompositeSourceAdapter(sourceAdapters)

    AdapterSet(modelComposite, sourceComposite)
  }
}