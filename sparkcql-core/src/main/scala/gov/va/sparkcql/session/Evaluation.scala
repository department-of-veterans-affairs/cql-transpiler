package gov.va.sparkcql.session

import scala.collection.JavaConverters._
import org.apache.spark.sql.{DataFrame}
import org.hl7.elm.r1.{VersionedIdentifier, Library, ExpressionDef}
import org.hl7.cql_annotations.r1.CqlToElmError
import gov.va.sparkcql.evaluator.EvaluationResult

case class Evaluation(parameters: Option[Map[String, Object]], output: Seq[LibraryEvaluation]) {
  def errors(): Seq[CqlToElmError] = {
    val allAnnotations = output.flatMap(_.library.getAnnotation().asScala)
    val filtered = allAnnotations.filter {
      case e: CqlToElmError => true
      case _ => false
    }
    filtered.map(f => f.asInstanceOf[CqlToElmError])
  }
}

case class LibraryEvaluation(library: Library, statements: Seq[StatementEvaluation])
case class StatementEvaluation(expressionDef: ExpressionDef, result: DataFrame)

object Evaluation {
  def apply(compilation: Seq[Library], evaluation: EvaluationResult): Evaluation = {
    val translatedOutput = evaluation.output.map(l => {
      val statementsTranslated = l.statements.map(s => StatementEvaluation(s.expressionDef, s.result))
      LibraryEvaluation(l.library, statementsTranslated)
    })
    new Evaluation(Some(evaluation.parameters), translatedOutput)
  }
}