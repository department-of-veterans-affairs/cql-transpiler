package gov.va.sparkcql.synthea

import scala.reflect.runtime.universe._
import gov.va.sparkcql.core.adapter.source.SourceConfiguration

case class SyntheaSourceConfiguration(size: PopulationSize) extends SourceConfiguration {
  
  val adapterFactoryType: Type = typeOf[SyntheaSourceFactory]
}