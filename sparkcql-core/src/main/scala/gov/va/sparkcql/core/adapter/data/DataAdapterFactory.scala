package gov.va.sparkcql.core.adapter.data

import gov.va.sparkcql.core.adapter.AdapterFactory

trait DataAdapterFactory extends AdapterFactory {
  
  def create(configuration: DataAdapterConfig): DataAdapter
  
  def create(): DataAdapter
}