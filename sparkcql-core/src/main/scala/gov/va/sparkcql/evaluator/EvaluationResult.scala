package gov.va.sparkcql.evaluator

import org.apache.spark.sql.{DataFrame}
import org.hl7.elm.r1.{VersionedIdentifier, Library, ExpressionDef}

case class EvaluationResult(parameters: Map[String, Object], output: List[LibraryResult])
case class LibraryResult(library: Library, statements: List[ExpressionDefResult])
case class ExpressionDefResult(expressionDef: ExpressionDef, result: DataFrame)