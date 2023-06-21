package gov.va.sparkcql.core.adapter.model

import gov.va.sparkcql.core.adapter.{Adapter, Configuration}

class CanonicalModelFactory extends ModelFactory {
  
  def isDefaultConfigurable(): Boolean = false
  
  def defaultConfiguration: Option[Configuration] = None

  def create(configuration: Option[ModelConfiguration]): ModelAdapter = {
    new CanonicalModelAdapter()
  }
}