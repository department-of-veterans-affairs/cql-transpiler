package gov.va.sparkcql.core.adapter.source

import scala.reflect.runtime.universe._

case class FileSourceAdapterConfig(path: String) extends SourceAdapterConfig {
  
  val adapterFactoryType: Type = typeOf[FileSourceAdapterFactory]
}