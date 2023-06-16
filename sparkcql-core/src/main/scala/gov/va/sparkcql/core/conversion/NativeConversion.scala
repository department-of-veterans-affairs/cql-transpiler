package gov.va.sparkcql.core.conversion

import scala.reflect.runtime.universe._
import gov.va.sparkcql.core.model.DataType
import javax.xml.namespace.QName

trait NativeConversion {

  implicit val qnameToDataType: Convertable[QName, DataType] = (source: QName) => {
    DataType(source.getNamespaceURI(), source.getLocalPart(), None)
  }
}