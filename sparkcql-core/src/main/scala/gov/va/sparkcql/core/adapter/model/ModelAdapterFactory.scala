package gov.va.sparkcql.core.adapter.model

import gov.va.sparkcql.core.adapter.AdapterFactory

trait ModelAdapterFactory extends AdapterFactory {
  
  def create(configuration: ModelAdapterConfig): ModelAdapter
  
  def create(): ModelAdapter
}