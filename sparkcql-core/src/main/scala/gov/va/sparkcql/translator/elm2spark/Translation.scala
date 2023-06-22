package gov.va.sparkcql.translator.elm2spark

import org.apache.spark.sql.{Dataset, Row}
import org.hl7.elm.r1.{VersionedIdentifier, Library, ExpressionDef}

case class Translation(parameters: Option[Map[String, Object]], output: Seq[LibraryTranslation])
case class LibraryTranslation(library: Library, statements: Seq[StatementTranslation])
case class StatementTranslation(expressionDef: ExpressionDef, result: Option[Dataset[Row]], libraryRef: VersionedIdentifier)