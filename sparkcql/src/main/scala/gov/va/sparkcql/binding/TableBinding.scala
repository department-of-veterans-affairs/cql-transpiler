package gov.va.sparkcql.binding

import scala.reflect.runtime.universe._
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}
import gov.va.sparkcql.model.fhir.r4._
import gov.va.sparkcql.model._
import org.apache.spark.sql.functions._
import gov.va.sparkcql.session.Session

final case class TableBindingSetting(schema: Option[String], tablePrefix: Option[String], tablePostfix: Option[String], indexColumnPrefix: Option[String], indexColumnPostfix: Option[String], resourceColumn: String)

class TableBinding(session: Session, settings: TableBindingSetting) extends Binding {
  
  import session.spark.implicits._

  override def retrieve[T <: BoundType: TypeTag](filter: Option[List[PredicateLike]]): Option[Dataset[T]] = {

    //if (table.schema.isDefined) s"${table.schema.get}.${table.table}" else s"${table.table}"
    val simpleBoundName = boundName[T]
    val tableName = if (settings.schema.isDefined) 
        s"${settings.schema.get}.${settings.tablePrefix.getOrElse("")}${simpleBoundName}${settings.tablePostfix.getOrElse("")}"
      else
        s"${settings.tablePrefix.getOrElse("")}${simpleBoundName}${settings.tablePostfix.getOrElse("")}"
    
    val ds = session.spark.table(tableName)
      .select(col(s"${settings.resourceColumn}.*"))
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

object TableBinding extends BindingFactory {
  override def create(session: Session, settings: Option[Map[String, Any]]): Binding = {
    new TableBinding(session, convertSettings[TableBindingSetting](settings))
  }
}