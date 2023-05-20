package gov.va.sparkcql.binding

import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}
import gov.va.sparkcql.model.fhir.r4._
import scala.reflect.runtime.universe._
import org.apache.spark.sql.functions._

final case class TableBindingConfig(system: String, tables: List[TableBindingConfigTable])
final case class TableBindingConfigTable(schema: Option[String] = None, table: String, code: String, primaryCodePath: Option[String] = None, resourceColumn: String, indexes: Option[List[TableBindingConfigTableIndex]] = None)
final case class TableBindingConfigTableIndex(column: String, path: String)

class TableBinding(spark: SparkSession, protected var configuration: TableBindingConfig) extends Bindable {

  import spark.implicits._

  protected def tableConfig(code: String) = {
    val t = configuration.tables.filter(_.code == code)
    if (t.isEmpty) None else Some(t.head)
  }

  protected def tableNomenclature(table: TableBindingConfigTable): String = {
    if (table.schema.isDefined) s"${table.schema.get}.${table.table}" else s"${table.table}"
  }

  override def retrieve[T <: Product: TypeTag](resourceType: Coding, filter: Option[List[PredicateLike]]): Option[Dataset[T]] = {
    val table = tableConfig(resourceType.code).getOrElse(throw new Exception(s"Table ${resourceType.code} not found."))
    
    val ds = spark.table(tableNomenclature(table))
      .select(col(s"${table.resourceColumn}.*"))
      .as[T]
    
    // TODO: Predicate pushdown
    // var x = filter.get.foreach(_ match {
    //   case CodingEqualityPredicate(property, value) => 
    //     ds.where(ds(property.code) === value.code)
    //   case ValueSetMembershipPredicate(property, value) => 
    //     val codeList = value.expansion.get.contains
    //       .get.map(x => x.code)
    //     ds.where(ds(property.code).isin(codeList))
    //   case DurationBetweenPredicate(property, start, end) => 
    //     ds.where(ds(property.code).between(start, end))
    // })
    
    Some(ds)
  }
}