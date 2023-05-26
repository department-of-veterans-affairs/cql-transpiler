package gov.va.sparkcql.binding

import org.apache.spark.sql.DataFrame
import gov.va.sparkcql.session.CqlSession
import gov.va.sparkcql.model.elm.{Code, ExpressionLike}
import org.apache.log4j.{Level, Logger}

class NullBinder() extends Binder {
  override def retrieve(session: CqlSession, bindableTypeCode: Code, filter: Option[List[ExpressionLike]]): Option[DataFrame] = {
    Logger.getLogger("org").warn("No binder set. Call CqlSession.withBinder to set one.")
    None
  }
}
