package gov.va.sparkcql.synthea

import scala.reflect.runtime.universe._
import gov.va.sparkcql.source.SourceConfiguration

case class SyntheaSourceConfiguration(size: PopulationSize) extends SourceConfiguration