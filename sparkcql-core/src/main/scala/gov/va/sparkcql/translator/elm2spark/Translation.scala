package gov.va.sparkcql.translator.elm2spark

import org.apache.spark.sql.{DataFrame}
import org.hl7.elm.r1.{VersionedIdentifier, Library, ExpressionDef}

case class Translation(parameters: Map[String, Object], output: List[LibraryTranslation])
case class LibraryTranslation(library: Library, statements: List[ExpressionDefTranslation])
case class ExpressionDefTranslation(expressionDef: ExpressionDef, result: DataFrame)