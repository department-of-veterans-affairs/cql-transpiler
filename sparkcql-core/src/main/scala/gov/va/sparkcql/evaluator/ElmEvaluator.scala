package gov.va.sparkcql.evaluator

import scala.collection.JavaConverters._
import org.apache.spark.sql.SparkSession
import gov.va.sparkcql.dataprovider.DataProvider
import gov.va.sparkcql.compiler.CqlCompilation
import gov.va.sparkcql.model.{Evaluation, LibraryEvaluation, StatementEvaluation}
import org.hl7.elm.r1._

class ElmEvaluator(spark: SparkSession, clinicalDataProvider: Option[DataProvider], terminologyDataProvider: Option[DataProvider]) { 
  def evalCompilation(compilation: CqlCompilation) = Evaluation(Map[ParameterDef, Object](), evalLibraries(compilation.libraries))
  def evalLibraries(libraries: Seq[Library]) = libraries.map(evalLibrary(_))
  def evalLibrary(library: Library) = LibraryEvaluation(library, evalStatements(library.getStatements()))
  def evalStatements(statements: Library.Statements) = statements.getDef().asScala.map(evalStatement(_))
  def evalStatement(expressionDef: ExpressionDef) = StatementEvaluation(expressionDef, null)
}