package gov.va.sparkcql.model
// TODO: Consider moving these into separate subpackage, perhaps leaving .model empty. Avoids collisions.
import scala.collection.JavaConverters._
import org.apache.spark.sql.{Dataset, Row}
import org.hl7.elm.r1.{Library, ParameterDef, ExpressionDef}
import org.hl7.elm.r1.TupleElementDefinition
import org.hl7.cql_annotations.r1.{CqlToElmBase, CqlToElmError, ErrorSeverity}

case class Evaluation(parameters: Map[ParameterDef, Object], statements: Seq[LibraryEvaluation]) {
  val hasError = {
    annotations().collect {
      case x: CqlToElmError => x
      case _ => None
    }
  }

  def annotations(): Seq[CqlToElmBase] = {
    statements.flatMap(_.library.getAnnotation().asScala.toSeq)
  }
}

case class LibraryEvaluation(library: Library, statements: Seq[StatementEvaluation])
case class StatementEvaluation(expressionDef: ExpressionDef, result: Dataset[Row])