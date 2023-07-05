package gov.va.sparkcql.translator

import org.apache.spark.sql.{DataFrame}
import org.hl7.elm.r1.{VersionedIdentifier, Library, ExpressionDef}
import scala.collection.JavaConverters._
import org.apache.spark.sql.{DataFrame}
import org.hl7.cql_annotations.r1.CqlToElmError

case class TranslationPack(parameters: Map[String, Object], output: List[LibraryTranslation]) {
  def errors(): Seq[CqlToElmError] = {
    val allAnnotations = output.flatMap(_.library.getAnnotation().asScala)
    val filtered = allAnnotations.filter {
      case e: CqlToElmError => true
      case _ => false
    }
    filtered.map(f => f.asInstanceOf[CqlToElmError])
  }  
}

case class LibraryTranslation(library: Library, statements: List[ExpressionDefTranslation])
case class ExpressionDefTranslation(expressionDef: ExpressionDef, result: DataFrame)