package gov.va.sparkcql.model

class CoreModelFactory extends ModelFactory {

  override def create(): Model = new CoreModel()
}
