package gov.va.sparkcql.core.adapter.model

import gov.va.sparkcql.core.adapter.Factory

trait ModelFactory extends Factory {
  
  def create(configuration: Option[ModelConfiguration]): ModelAdapter
}