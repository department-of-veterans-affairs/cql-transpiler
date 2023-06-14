package gov.va.sparkcql.core.adapter.model

trait ModelAdapterFactory {

  def create(): ModelAdapter
}