package gov.va.sparkcql.core.model

import scala.collection.JavaConverters._
import org.apache.spark.sql.{Dataset, Row}
import org.hl7.elm.r1.{VersionedIdentifier, Library, ExpressionDef}
import gov.va.sparkcql.core.model.elm.ElmTypes
import org.hl7.cql_annotations.r1.CqlToElmError

case class Evaluation(parameters: Option[Map[String, ElmTypes.Any]], output: Seq[LibraryEvaluation]) {
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
case class StatementEvaluation(expressionDef: ExpressionDef, result: Option[Dataset[Row]], libraryRef: VersionedIdentifier)