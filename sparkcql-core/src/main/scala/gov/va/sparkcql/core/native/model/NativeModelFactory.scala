package gov.va.sparkcql.core.native.model

import gov.va.sparkcql.core.model.ModelFactory
import gov.va.sparkcql.core.model.Model

class NativeModelFactory extends ModelFactory {

  override def create(): Model = new NativeModel()
}
