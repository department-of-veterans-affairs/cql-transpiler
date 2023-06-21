package gov.va.sparkcql.core.adapter

import scala.reflect._
import scala.collection.JavaConverters._
import java.util.ServiceLoader
import gov.va.sparkcql.core.Log
import gov.va.sparkcql.core.adapter.source.{SourceAdapter, SourceAdapterFactory}
import gov.va.sparkcql.core.adapter.model.{ModelAdapter, ModelAdapterFactory}
import gov.va.sparkcql.core.adapter.data.{DataAdapter, DataAdapterFactory}
import scala.reflect.ClassTag

class AdapterServiceLoader() {
  
    //   if (modelAdapterFactories.length == 0) {
    //     Log.warn("No model adapters specified.")
    //   }


  lazy val sourceAdapterFactories: List[SourceAdapterFactory] = {
    loadAdapterFactories[SourceAdapterFactory](true)
  }

  val modelAdapterFactories: List[ModelAdapterFactory] = {
    loadAdapterFactories[ModelAdapterFactory](true)
  }

  val dataAdapterFactories: List[DataAdapterFactory] = {
    loadAdapterFactories[DataAdapterFactory](true)
  }

  def loadAdapterFactories[T : ClassTag](warnOnEmpty: Boolean): List[T] = {
    var cls = classTag[T].runtimeClass
    var factories = ServiceLoader.load(cls).iterator().asScala.toList
    if (factories.length == 0 && warnOnEmpty) {
      Log.warn(s"No ${cls.getSimpleName()} service providers found on the classpath.")
    }
    factories.asInstanceOf[List[T]]
  }
}