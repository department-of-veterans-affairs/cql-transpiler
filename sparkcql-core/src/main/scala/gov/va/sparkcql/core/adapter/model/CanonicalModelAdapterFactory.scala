package gov.va.sparkcql.core.adapter.model

class CanonicalModelAdapterFactory extends ModelAdapterFactory {
  
  def isDefaultConfigurable(): Boolean = true
  
  def create(configuration: ModelAdapterConfig): ModelAdapter = new CanonicalModelAdapter()

  def create(): ModelAdapter = new CanonicalModelAdapter()
}