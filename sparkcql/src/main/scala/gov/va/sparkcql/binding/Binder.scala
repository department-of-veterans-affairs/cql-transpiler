package gov.va.sparkcql.binding

import scala.reflect.runtime.universe._
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.Row
import gov.va.sparkcql.session.CqlSession
import gov.va.sparkcql.model.elm.{Code, ValueSet, ExpressionLike}
import gov.va.sparkcql.model.elm.primitive._

/**
  * 
  */
trait Binder {

  def retrieve(session: CqlSession, bindableTypeCode: Code, filter: Option[List[ExpressionLike]] = None): Option[DataFrame]
  
}