package gov.va.sparkcql.core.session

import scala.collection.JavaConverters._
import org.apache.spark.sql.{Dataset, Row}
import org.hl7.elm.r1.{Library, ParameterDef, ExpressionDef}
import org.hl7.elm.r1.TupleElementDefinition
import org.hl7.cql_annotations.r1.{CqlToElmBase, CqlToElmError, ErrorSeverity}
import gov.va.sparkcql.core.model.elm.ElmTypes

case class Evaluation(parameters: Option[Map[String, ElmTypes.Any]], libraries: Seq[LibraryEvaluation])
case class LibraryEvaluation(library: Library, statements: Seq[StatementEvaluation])
case class StatementEvaluation(expressionDef: ExpressionDef, result: Dataset[Row])