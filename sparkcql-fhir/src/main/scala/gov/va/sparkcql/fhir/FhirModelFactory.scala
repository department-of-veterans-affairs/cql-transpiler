package gov.va.sparkcql.fhir

import gov.va.sparkcql.model.{Model, ModelFactory}

class FhirModelFactory extends ModelFactory {

  override def create(): Model = {
    new FhirModel()
  }
}