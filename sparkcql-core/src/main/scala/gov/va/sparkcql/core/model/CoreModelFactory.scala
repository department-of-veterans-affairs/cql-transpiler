package gov.va.sparkcql.core.model

class CoreModelFactory extends ModelFactory {

  override def create(): Model = new CoreModel()
}
