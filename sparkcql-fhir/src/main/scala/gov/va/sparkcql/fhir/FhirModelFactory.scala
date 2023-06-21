package gov.va.sparkcql.fhir

import gov.va.sparkcql.core.adapter.model.{ModelAdapter, ModelFactory}
import gov.va.sparkcql.core.adapter.Configuration
import gov.va.sparkcql.core.adapter.model.ModelConfiguration

class FhirModelFactory extends ModelFactory {

  override def isDefaultConfigurable: Boolean = true

  override def defaultConfiguration: Option[Configuration] = Some(FhirModelConfiguration())

  override def create(configuration: Option[ModelConfiguration]): ModelAdapter = {
    new FhirModelAdapter()
  }

}