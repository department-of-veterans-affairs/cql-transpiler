package gov.va.sparkcql.binding

import scala.reflect.runtime.universe._
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._
import gov.va.sparkcql.session.CqlSession
import gov.va.sparkcql.model.elm.{Code, ExpressionLike}

final case class TableInfo(table: String, schema: Option[String], resourceColumn: Option[String] = Some("resource_data"), indexes: Option[List[IndexInfo]])
final case class IndexInfo(code: Code, columnName: String)

class TableBinder() extends Binder {
  
  val DefaultResourceColumnName = "resource_data"

  /**
    * 
    *
    * @param code
    * @return
    */
  def tableInfo(code: Code): TableInfo = {
    TableInfo(code.code, None, Some(DefaultResourceColumnName), None)
  }

  def tableRef(code: Code): String = {
    val info = tableInfo(code)

    if (info.schema.isDefined)
      s"${info.schema.get}.${info.table}"
    else
      s"${info.table}"
  }

  override def retrieve(session: CqlSession, bindableTypeCode: Code, filter: Option[List[ExpressionLike]]): Option[DataFrame] = {
    import session.spark.implicits._
    
    val info = tableInfo(bindableTypeCode)
    val table = tableRef(bindableTypeCode)
    
    val ds = session.spark.table(table)
      .select(col(s"${info.resourceColumn.getOrElse(DefaultResourceColumnName)}.*"))
    
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