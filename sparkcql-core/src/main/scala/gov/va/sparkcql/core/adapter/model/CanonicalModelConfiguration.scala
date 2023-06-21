package gov.va.sparkcql.core.adapter.model

import scala.reflect.runtime.universe._

final case class CanonicalModelConfiguration() extends ModelConfiguration {
  
  val adapterFactoryType: Type = typeOf[CanonicalModelFactory]
}