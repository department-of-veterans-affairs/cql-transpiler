package gov.va.sparkcql.binding.factory

import gov.va.sparkcql.model.elm.VersionedIdentifier
import gov.va.sparkcql.binding.strategy.BindingStrategy

trait BindingFactory {
  def create(dataTypeIdentifier: VersionedIdentifier): BindingStrategy
}