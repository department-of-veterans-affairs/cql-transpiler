package gov.va.sparkcql.binding

import org.apache.spark.sql.{DataFrame, SparkSession}
import gov.va.sparkcql.model.fhir._
import gov.va.sparkcql.model.fhir.Primitive._
import gov.va.sparkcql.model.fhir.Coding
import scala.reflect.runtime.universe._
import org.apache.spark.sql.Dataset

final case class TableBindingConfig(system: String, resourceTypes: List[TableBindingConfigType])
final case class TableBindingConfigType(schema: Option[String], table: String, code: String, primaryCodePath: Option[String], resourceColumn: String, indexes: Option[List[TableBindingConfigTypeIndex]])
final case class TableBindingConfigTypeIndex(column: String, path: String)

class TableBinding(spark: SparkSession, configuration: TableBindingConfig) extends Binding(spark) {

  def resolve[T <: Product : TypeTag](resourceType: Coding, code: Option[Coding], startDate: Option[DateTime], endDate: Option[DateTime]): Dataset[T] = ???
}