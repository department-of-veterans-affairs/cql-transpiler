package gov.va.sparkcql.fhir

import gov.va.sparkcql.adapter.model.{ModelAdapter, ModelAdapterFactory}
import gov.va.sparkcql.di.ComponentConfiguration

class FhirModelAdapterFactory extends ModelAdapterFactory {

  override def create(configuration: ComponentConfiguration): ModelAdapter = new FhirModelAdapter()
}