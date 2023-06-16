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
import gov.va.sparkcql.core.model.{DataType, StatementEvaluation}
import gov.va.sparkcql.core.Log
import gov.va.sparkcql.core.model.{Evaluation, LibraryEvaluation, StatementEvaluation}
import gov.va.sparkcql.core.model.elm.ElmTypes.DateTimeInterval

class ElmR1ToSparkTranslator(sourceAdapters: Option[SourceAdapter], modelAdapters: Option[ModelAdapter], spark: SparkSession) 
    extends ElmToSparkTranslator(sourceAdapters, modelAdapters, spark) {

  def translate(parameters: Option[Map[String, ElmTypes.Any]], libraryCollection: Seq[Library]): Evaluation = {
    val initialCtx = TranslationContext(parameterValues = parameters)
    val libraryEvals = libraryCollection.flatMap(l => {
      val libEval = l.eval(initialCtx).to[LibraryEvaluation]
      Some(libEval)
    })
    Evaluation(parameters, libraryEvals)
  }

  /**
  * Purpose is to provide eval dispatch and exhaustiveness checks for all ELM types. The type of each ELM
  * node is unknown at compile time and the ELM isn't inheritable which limits our ability to use polymorphism. 
  * This method converts a runtime type to a type known at compile time allowing the correct polymorpyhic
  * implementation.
  */
  protected def dispatch(node: Element, ctx: TranslationContext): Any = {
    node match {
      case n: AliasedQuerySource => visit(n, ctx)
      case n: And => visit(n, ctx)
      case n: End => visit(n, ctx)
      case n: ExpressionDef => visit(n, ctx)
      case n: In => visit(n, ctx)
      case n: Library => visit(n, ctx)
      case n: ParameterRef => visit(n, ctx)
      case n: Property => visit(n, ctx)
      case n: Query => visit(n, ctx)
      case n: Retrieve => visit(n, ctx)
      case n: SingletonFrom => None
      
      case n: Null => None
      case null => None
      case n => {
        Log.warn(s"Translation of ELM type '${n.getClass().getTypeName()}' to Spark is not implemented.")
        None
      }
    }
  }

  protected def visit(n: Library, ctx: TranslationContext): LibraryEvaluation = {
    val errors = n.getAnnotation().asScala.filter(p => p.isInstanceOf[CqlToElmError])

    if (errors.length == 0) {
      val libraryExpressionDefs = n.getStatements().getDef().asScala.toSeq
      val statementEvals = libraryExpressionDefs.map(_.eval(ctx.copy(library = Some(n))).to[StatementEvaluation])
      LibraryEvaluation(n, statementEvals)
    } else {
      LibraryEvaluation(n, Seq[StatementEvaluation]())
    }
  }

  protected def visit(n: ExpressionDef, ctx: TranslationContext): StatementEvaluation = {
    val result = n.getExpression().eval(ctx).to[Option[Dataset[Row]]]
    val libraryRef = ctx.library.get.getIdentifier()
    StatementEvaluation(n, result, libraryRef)
  }

  protected def visit(n: CodeSystemRef, ctx: TranslationContext): Unit = {
    ???
  }

  protected def visit(n: Query, ctx: TranslationContext): Option[Dataset[Row]] = {
    val querySource = n.getSource().asScala.toSeq.flatMap(_.eval(ctx).to[Option[Dataset[Row]]]).headOption
    val newCtx = ctx.copy(dataset = querySource.headOption)

    val aggregateClause = n.getAggregate().eval(newCtx)
    val letClause = n.getLet().asScala.map(_.eval(newCtx))
    val relationshipClause = n.getRelationship().asScala.map(_.eval(newCtx))
    val returnClause = n.getReturn().eval(newCtx)
    val sortClause = n.getSort().eval(newCtx)
    val whereClause = n.getWhere().eval(newCtx).to[Column]

    newCtx.dataset.map(_.filter(whereClause))
  }

  protected def visit(n: Retrieve, ctx: TranslationContext): Option[Dataset[Row]] = {
    sourceAdapters match {
      case Some(value) => value.read(DataType(QName(n.getDataType())))
      case None => None
    }
  }

  protected def visit(n: AliasedQuerySource, ctx: TranslationContext): Option[Dataset[Row]] = {
    val source = n.getExpression().eval(ctx).to[Option[Dataset[Row]]]

    if (n.getAlias() != null) {
      source.map(_.alias(n.getAlias()))
    } else {
      source
    }
  }

  protected def visit(n: And, ctx: TranslationContext): Column = {
    n.getOperand().asScala.foldLeft[Column](lit(true))((c, expr) => {
      val op = expr.eval(ctx).to[Column]
      c.and(op)
    })
  }

  protected def visit(n: In, ctx: TranslationContext): Column = {
    assert(n.getOperand().size() == 2, "Unexpected length for In operator")
    val leftOp = n.getOperand().get(0).eval(ctx).to[Column]
    val rightOp = n.getOperand().get(1).eval(ctx).to[Column]
    leftOp.between(rightOp("low"),rightOp("high"))
  }

  protected def visit(n: End, ctx: TranslationContext): Column = {
    val operand: Column = n.getOperand().eval(ctx).to[Column]
    val endProperty = "end"   // TODO
    operand.apply(endProperty)
  }

  protected def visit(n: Property, ctx: TranslationContext): Column = {
    col(s"${n.getScope()}.${n.getPath()}")
  }

  protected def visit(n: ParameterRef, ctx: TranslationContext): Column = {
    val param = ctx.parameterValues.map(_.filter(_._1 == n.getName()).head._2)
    val p = param.getOrElse(throw new Exception(s"Unknown parameter ${n.getName()}"))
    ElmTypeConversion.convert(p)
  }
}