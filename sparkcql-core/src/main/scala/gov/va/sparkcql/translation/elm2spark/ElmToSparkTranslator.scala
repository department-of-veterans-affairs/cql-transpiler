package gov.va.sparkcql.translation.elm2spark

import scala.collection.JavaConverters._
import org.apache.spark.sql.{SparkSession, Dataset, Row}
import org.hl7.elm.r1._
import org.hl7.cql_annotations.r1._
import gov.va.sparkcql.dataprovider.{DataProvider, DataAdapter}
import gov.va.sparkcql.dataprovider.DataTypeRef
import gov.va.sparkcql.model.elm.ElmTypes

class ElmToSparkTranslator(spark: SparkSession, clinicalDataAdapter: Option[DataAdapter], terminologyDataAdapter: Option[DataAdapter]) {

  def translate(parameters: Option[Map[String, ElmTypes.Any]], libraryCollection: Seq[Library]): Seq[ElmDatasetLink] = {
    val initialCtx = TranslationContext(parameterValues = parameters)
    libraryCollection.flatMap(eval(initialCtx, _))
  }

  protected def eval(ctx: TranslationContext, library: Library): Seq[ElmDatasetLink] = {
    val libraryExpressionDefs = library.getStatements().getDef().asScala.toSeq
    libraryExpressionDefs.map(eval(ctx.copy(library = Some(library)), _))
  }

  protected def eval(ctx: TranslationContext, expressionDef: ExpressionDef): ElmDatasetLink = {
    ElmDatasetLink(
      expressionDef, 
      eval(ctx, expressionDef.getExpression()).asInstanceOf[Dataset[Row]], 
      ctx.library.get.getIdentifier())
  }

  protected def eval(ctx: TranslationContext, exp: Expression): Object = {
    exp match {

        case exp: Query =>
          val sources = exp.getSource().asScala.map(aliasedQuerySource => {
            val source = eval(ctx, aliasedQuerySource.getExpression()).asInstanceOf[Dataset[Row]]
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