package gov.va.sparkcql.evaluator

import scala.collection.JavaConverters._
import org.apache.spark.sql.{Dataset, Row}
import org.hl7.elm.r1.{Library, ParameterDef, ExpressionDef}
import org.hl7.elm.r1.TupleElementDefinition
import org.hl7.cql_annotations.r1.{CqlToElmBase, CqlToElmError, ErrorSeverity}
import gov.va.sparkcql.common.helper.CqfHelper

trait EvaluationNode

case class Evaluation(parameters: Map[ParameterDef, Object], libraries: Seq[LibraryEvaluation]) extends EvaluationNode
case class LibraryEvaluation(library: Library, statements: Seq[StatementEvaluation]) extends EvaluationNode
case class StatementEvaluation(expressionDef: ExpressionDef, result: Dataset[Row]) extends EvaluationNode