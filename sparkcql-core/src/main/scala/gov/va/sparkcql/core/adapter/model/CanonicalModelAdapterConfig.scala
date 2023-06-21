package gov.va.sparkcql.core.adapter.model

import scala.reflect.runtime.universe._

final case class CanonicalModelAdapterConfig() extends ModelAdapterConfig {
  
  val adapterFactoryType: Type = typeOf[CanonicalModelAdapterFactory]
}