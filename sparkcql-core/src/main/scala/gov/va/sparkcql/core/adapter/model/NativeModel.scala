package gov.va.sparkcql.core.adapter.model

class NativeModel extends ModelAdapterFactory {
  def create(): ModelAdapter = new NativeModelAdapter()
}
