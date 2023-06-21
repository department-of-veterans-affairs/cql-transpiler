package gov.va.sparkcql.synthea

import scala.reflect.runtime.universe._
import gov.va.sparkcql.core.adapter.source.SourceAdapterConfig

case class SyntheaSourceAdapterConfig(size: PopulationSize) extends SourceAdapterConfig {
  
  val adapterFactoryType: Type = typeOf[SyntheaSourceAdapterFactory]
}