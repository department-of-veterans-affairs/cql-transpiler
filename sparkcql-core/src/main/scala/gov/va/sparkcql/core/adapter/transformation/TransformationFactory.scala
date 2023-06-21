package gov.va.sparkcql.core.adapter.transformation

import gov.va.sparkcql.core.adapter.{Adapter, Factory, Configuration}

trait TransformationFactory extends Factory {
  
  def create(configuration: Configuration): Adapter
  
  def create(): TransformationAdapter
}