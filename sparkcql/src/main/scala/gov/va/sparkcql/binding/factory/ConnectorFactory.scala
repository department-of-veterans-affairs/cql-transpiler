package gov.va.sparkcql.binding.factory

import gov.va.sparkcql.model.fhir.Coding
import gov.va.sparkcql.binding.Binding

trait BindingFactory {
  def create(dataTypeIdentifier: Coding): Binding
}