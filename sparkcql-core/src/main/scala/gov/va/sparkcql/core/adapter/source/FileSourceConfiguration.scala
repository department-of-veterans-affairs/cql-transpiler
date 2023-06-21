package gov.va.sparkcql.core.adapter.source

import scala.reflect.runtime.universe._

case class FileSourceConfiguration(path: String) extends SourceConfiguration {
  
  val adapterFactoryType: Type = typeOf[FileSourceFactory]
}