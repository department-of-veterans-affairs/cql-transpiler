package gov.va.sparkcql.fhir

import scala.reflect.runtime.universe._
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.Encoders
import gov.va.sparkcql.core.model.DataType

object FhirDataType {
    
  def apply(resourceType: String): DataType = DataType("http://hl7.org/fhir", resourceType, None)

  def apply(resourceType: String, version: String): DataType = DataType("http://hl7.org/fhir", resourceType, Some(version))
}