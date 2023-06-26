package gov.va.sparkcql.translator.elm2spark

import org.apache.spark.sql.{DataFrame}
import org.hl7.elm.r1.{VersionedIdentifier, Library, ExpressionDef}

case class Translation(parameters: Map[String, Object], output: Seq[LibraryTranslation])
case class LibraryTranslation(library: Library, statements: Seq[ExpressionDefTranslation])
case class ExpressionDefTranslation(expressionDef: ExpressionDef, result: DataFrame)