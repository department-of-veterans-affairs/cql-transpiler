package gov.va.sparkcql.evaluator

import scala.collection.JavaConverters._
import org.apache.spark.sql.{SparkSession, Dataset, Row}
import org.hl7.elm.r1._
import org.hl7.cql_annotations.r1._
import gov.va.sparkcql.dataprovider.{DataProvider, DataAdapter}
import gov.va.sparkcql.compiler.Compilation
import gov.va.sparkcql.dataprovider.DataTypeRef

class Evaluator(parameters: Option[Map[String, Object]], spark: SparkSession, clinicalDataAdapter: Option[DataAdapter], terminologyDataAdapter: Option[DataAdapter]) { 

  def eval(compilation: Compilation): Evaluation = {
    Evaluation(Map[ParameterDef, Object](), eval(compilation.libraries))
  }

  def eval(libraries: Seq[Library]): Seq[LibraryEvaluation] = {
    libraries.map(eval(_).asInstanceOf[LibraryEvaluation])
  }

  def eval(library: Library): LibraryEvaluation = {
    val errors = library.getAnnotation().asScala.collect {
      case x: CqlToElmError => x
    }

    if (errors.length == 0) {
      LibraryEvaluation(library, eval(library.getStatements()))
    } else {
      errors.foreach(f => System.err.println(f.toString()))
      LibraryEvaluation(library, Seq())
    }
  }

  def eval(statements: Library.Statements): Seq[StatementEvaluation] = {
    statements.getDef().asScala.map(eval(_))
  }

  def eval(expressionDef: ExpressionDef): StatementEvaluation = {
    StatementEvaluation(expressionDef, eval(expressionDef.getExpression()).asInstanceOf[Dataset[Row]])
  }

  def eval(exp: Expression): Object = {
    exp match {

        case exp: Query =>
          val sources = exp.getSource().asScala.map(aliasedQuerySource => {
            val source = eval(aliasedQuerySource.getExpression()).asInstanceOf[Dataset[Row]]
            if (aliasedQuerySource.getAlias() != null) {
              source.alias(aliasedQuerySource.getAlias())
            } else {
              source
            }
          })
          // TODO: Apply clauses and return single type
          sources.head

      case exp: Retrieve => 
        clinicalDataAdapter.get.read(DataTypeRef(
          exp.getDataType().getNamespaceURI(),
          exp.getDataType().getLocalPart(), None))

      case _ => null
    }
  }
}