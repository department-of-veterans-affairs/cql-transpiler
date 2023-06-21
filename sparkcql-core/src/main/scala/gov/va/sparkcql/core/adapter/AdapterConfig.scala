package gov.va.sparkcql.core.adapter

import scala.reflect.runtime.universe._

trait AdapterConfig {
  val adapterFactoryType: Type
}