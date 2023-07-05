package gov.va.sparkcql.di

import scala.reflect._
import java.util.ServiceLoader
import scala.collection.JavaConverters._

object Components {
  
  def load[T](cls: Class[T]): List[T] = ServiceLoader.load(cls).iterator().asScala.toList

  def load[T : ClassTag](): List[T] = {
    val cls = classTag[T].runtimeClass
    val factories = ServiceLoader.load(cls).iterator().asScala.toList
    factories.asInstanceOf[List[T]]
  }    
}