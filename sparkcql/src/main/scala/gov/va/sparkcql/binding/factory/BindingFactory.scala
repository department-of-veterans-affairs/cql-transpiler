package gov.va.sparkcql.binding.factory

import gov.va.sparkcql.model.fhir.Coding
import gov.va.sparkcql.binding.strategy.BindingStrategy

trait BindingFactory {
  def create(dataTypeIdentifier: Coding): BindingStrategy
}