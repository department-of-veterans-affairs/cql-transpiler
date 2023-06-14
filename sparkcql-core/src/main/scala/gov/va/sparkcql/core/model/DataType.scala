package gov.va.sparkcql.core.model

import scala.reflect.runtime.universe._
import gov.va.sparkcql.core.model.xsd.QName
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.Encoders

case class DataType(system: String, name: String, version: Option[String]) {
  override def toString(): String = {
    s"${system}/${name}"
  }
}

object DataType {
    
  /**
    * https://download.oracle.com/otn-pub/jcp/jaxws-2.2-mrel3-evalu-oth-JSpec/jaxws-2_2-mrel3-spec.pdf?AuthParam=1686669184_ba3f6eff178f0a7a260ec62d4b3570a4
    *
    * @return
    */
  def apply[T <: Product : TypeTag](): DataType = {
    val theType = typeOf[T]
    val fullTypeName = theType.typeSymbol.fullName
    val tokens = fullTypeName.split('.')
    assert(tokens.length >= 2, "Insufficient namespace tokens")
    val baseUrl = s"http://${tokens(1)}.${tokens(0)}"
    val dirs = tokens.slice(2, tokens.length - 1)
    val system = Array(baseUrl, dirs.mkString("/")).mkString("/")
    val name = tokens.last
    
    DataType(system, name, None)
  }

  def apply(qname: QName): DataType = {
    DataType(qname.uri, qname.name, None)
  }
}