package gov.va.sparkcql.core.adapter

import scala.reflect.runtime.universe._

trait Configuration {
  val adapterFactoryType: Type
}