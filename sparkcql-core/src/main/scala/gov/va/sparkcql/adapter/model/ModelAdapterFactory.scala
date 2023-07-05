package gov.va.sparkcql.adapter.model

import gov.va.sparkcql.di.ComponentConfiguration

trait ModelAdapterFactory {
  
  def create(configuration: ComponentConfiguration): ModelAdapter
}