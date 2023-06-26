package gov.va.sparkcql.translator.elm2spark

import org.apache.spark.sql.DataFrame
import javax.xml.namespace.QName

case class QueryContext(
  source: List[QuerySourceContext]
) extends Contextual

case class QuerySourceContext(
  dataType: QName,
  name: String,
  df: DataFrame
) extends Contextual