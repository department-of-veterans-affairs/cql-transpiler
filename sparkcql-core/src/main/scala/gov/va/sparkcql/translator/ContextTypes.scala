// package gov.va.sparkcql.translator

// import org.apache.spark.sql.DataFrame
// import javax.xml.namespace.QName

// trait ContextType

// case class QueryContext(
//   source: List[QuerySourceContext]
// ) extends ContextType

// case class QuerySourceContext(
//   dataType: QName,
//   name: Option[String],
//   df: DataFrame
// ) extends ContextType

// case class CallContext(parameter: Map[String, Object]) extends ContextType