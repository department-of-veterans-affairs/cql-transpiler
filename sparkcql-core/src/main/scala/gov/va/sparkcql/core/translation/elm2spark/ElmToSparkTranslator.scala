package gov.va.sparkcql.core.translation.elm2spark

import scala.collection.JavaConverters._
import org.apache.spark.sql.{SparkSession, Dataset, Row, Column}
import org.apache.spark.sql.functions._
import org.hl7.elm.r1._
import org.hl7.elm.r1.CodeSystemRef
import org.hl7.cql_annotations.r1._
import gov.va.sparkcql.core.adapter.source.SourceAdapter
import gov.va.sparkcql.core.adapter.model.ModelAdapter
import gov.va.sparkcql.core.model.elm.ElmTypes
import gov.va.sparkcql.core.model.xsd.QName
import gov.va.sparkcql.core.model.DataType

class ElmToSparkTranslator(sourceAdapters: Option[SourceAdapter], modelAdapters: Option[ModelAdapter], spark: SparkSession) {

  def translate(parameters: Option[Map[String, ElmTypes.Any]], libraryCollection: Seq[Library]): Seq[ElmDatasetLink] = {
    val initialCtx = TranslationContext(parameterValues = parameters)
    libraryCollection.flatMap(eval(_, initialCtx))
  }

  protected def eval(n: Element, ctx: TranslationContext): Any = {
    n match {
      case n: Library => eval(n, ctx)
      case n: ExpressionDef => eval(n, ctx)
      case n: Query => eval(n, ctx)
      case n: Retrieve => eval(n, ctx)
      case n: In => eval(n, ctx)
      case n: End => eval(n, ctx)
      case n: Property => eval(n, ctx)
      case null => null
      case n => throw new Exception(s"Translation of ELM type '${n.getClass().getTypeName()}' to Spark is not implemented.")
    }
  }

  protected def eval(n: Library, ctx: TranslationContext): Seq[ElmDatasetLink] = {
    val libraryExpressionDefs = n.getStatements().getDef().asScala.toSeq
    libraryExpressionDefs.map(eval(_, ctx.copy(library = Some(n))))
  }

  protected def eval(expressionDef: ExpressionDef, ctx: TranslationContext): ElmDatasetLink = {
    ElmDatasetLink(
      expressionDef,
      eval(expressionDef.getExpression(), ctx).asInstanceOf[Option[Dataset[Row]]], 
      ctx.library.get.getIdentifier())
  }

  protected def eval(n: CodeSystemRef, ctx: TranslationContext): Unit = {
    ???
  }

  protected def eval(n: Query, ctx: TranslationContext): Option[Dataset[Row]] = {
    val querySource = n.getSource().asScala.toSeq.map(eval(_, ctx))
    assert(querySource.size <= 1, "Unexpectedly found multiple sources in query expression.")
    val newCtx = ctx.copy(dataset = Some(querySource.head.get))

    val queryAggregate = eval(n.getAggregate(), newCtx)
    val queryLet = n.getLet().asScala.map(eval(_, newCtx))
    val queryRelationship = n.getRelationship().asScala.map(eval(_, newCtx))
    val queryReturn = eval(n.getReturn(), newCtx)
    val querySort = eval(n.getSort(), newCtx)
    val queryWhere = eval(n.getWhere(), newCtx)

    Some(newCtx.dataset.get)
  }

  protected def eval(n: Retrieve, ctx: TranslationContext): Option[Dataset[Row]] = {
    sourceAdapters.get.read(DataType(QName(n.getDataType())))
  }

  protected def eval(n: AliasedQuerySource, ctx: TranslationContext): Option[Dataset[Row]] = {
    val source = eval(n.getExpression(), ctx).asInstanceOf[Option[Dataset[Row]]]
    if (n.getAlias() != null) {
      Some(source.get.alias(n.getAlias()))
    } else {
      source
    }
  }

  protected def eval(n: In, ctx: TranslationContext): Unit = {
    val ds = ctx.dataset.get
    n.getOperand().asScala.foreach(o => {

      val col = eval(o, ctx)
    })
    // ds.filter(col("").between(lit(""), lit("")))
    ???
  }

  protected def eval(n: End, ctx: TranslationContext): Column = {
    val operand = eval(n.getOperand(), ctx)
    col("")
  }

  protected def eval(n: Property, ctx: TranslationContext): Column = {
    col(s"${n.getScope()}.${n.getPath()}")
  }
}