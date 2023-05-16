package gov.va.sparkcql.binding.strategy

import org.apache.spark.sql.{DataFrame, SparkSession}
import gov.va.sparkcql.model.fhir._
import gov.va.sparkcql.model.fhir.Primitive._

final case class TableBindingConfig(system: String, resourceTypes: List[TableBindingConfigType])
final case class TableBindingConfigType(schema: Option[String], table: String, code: String, primaryCodePath: Option[String], resourceColumn: String, indexes: Option[List[TableBindingConfigTypeIndex]])
final case class TableBindingConfigTypeIndex(column: String, path: String)

class TableBinding(configuration: TableBindingConfig) extends BindingStrategy {
  def resolve(spark: SparkSession, resourceType: Coding, code: Option[Coding], startDate: Option[DateTime], endDate: Option[DateTime]): DataFrame = {
    throw new Exception("Not Imp")
  }
}