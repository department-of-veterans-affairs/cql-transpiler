package gov.va.sparkcql.fhir

import gov.va.sparkcql.core.adapter.model.ModelConfiguration
import scala.reflect.runtime.universe._

final case class FhirModelConfiguration() extends ModelConfiguration {

  val adapterFactoryType: Type = typeOf[FhirModelFactory]
}