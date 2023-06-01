package gov.va.sparkcql.dataprovider.clinical

import org.apache.spark.sql.{Dataset, Row}
import org.hl7.elm.r1.CodeFilterElement
import org.hl7.elm.r1.DateFilterElement
import org.hl7.elm.r1.OtherFilterElement
import org.hl7.elm.r1.Code

sealed trait RetrieveFilterElement
final case class RetrieveCodeFilter() extends CodeFilterElement with RetrieveFilterElement
final case class RetrieveDateFilter() extends DateFilterElement with RetrieveFilterElement
final case class RetrieveOtherFilter() extends OtherFilterElement with RetrieveFilterElement

trait Retrievable {

  def isDefined(dataType: Code): Boolean

  def retrieve(dataType: Code, filter: Option[List[RetrieveFilterElement]]): Option[Dataset[Row]]
}