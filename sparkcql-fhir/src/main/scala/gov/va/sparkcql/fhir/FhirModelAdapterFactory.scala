package gov.va.sparkcql.fhir

import gov.va.sparkcql.core.adapter.model.{ModelAdapterFactory, ModelAdapter}
import gov.va.sparkcql.core.adapter.model.ModelAdapterConfig

class FhirModelAdapterFactory extends ModelAdapterFactory {

  def isDefaultConfigurable(): Boolean = true

  def create(configuration: ModelAdapterConfig): ModelAdapter = new FhirModelAdapter()
  
  def create(): ModelAdapter = new FhirModelAdapter()
}