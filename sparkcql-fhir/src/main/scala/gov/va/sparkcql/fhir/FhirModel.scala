package gov.va.sparkcql.fhir

import gov.va.sparkcql.core.adapter.model.{ModelAdapterFactory, ModelAdapter}

class FhirModel extends ModelAdapterFactory {

  override def create(): ModelAdapter = new FhirModelAdapter()
}

object FhirModel {
  def apply(): ModelAdapterFactory = new FhirModel()
}