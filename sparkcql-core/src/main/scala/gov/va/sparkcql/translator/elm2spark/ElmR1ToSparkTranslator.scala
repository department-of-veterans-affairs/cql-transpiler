package gov.va.sparkcql.translator.elm2spark

import scala.collection.JavaConverters._
import org.apache.spark.sql.{SparkSession, DataFrame, Column, Dataset, Row}
import org.apache.spark.sql.functions._
import org.hl7.elm.{r1 => elm}
import org.hl7.cql_annotations.r1._
import gov.va.sparkcql.converter.Converter._
import javax.xml.namespace.QName
import gov.va.sparkcql.logging.Log
import java.time.{ZonedDateTime, LocalDate, LocalDateTime}
import java.sql.{Timestamp}
import gov.va.sparkcql.model.Model
import gov.va.sparkcql.source.Source

class ElmR1ToSparkTranslator(models: List[Model], sources: List[Source], spark: SparkSession) 
    extends ElmToSparkTranslator(models, sources, spark) {

  def translate(parameters: Map[String, Object], libraryCollection: Seq[elm.Library]): Translation = {
    val initialContext = ContextStack().push(CallContext(parameters))
    val libraryEvals = libraryCollection.flatMap(l => {
      val libEval = l.eval(initialContext).castTo[LibraryTranslation]
      Some(libEval)
    })
    Translation(parameters, libraryEvals)
  }

  /**
  * Purpose is to provide eval dispatch and exhaustiveness checks for all ELM types. The type of each ELM
  * node is unknown at compile time and the ELM isn't inheritable which limits our ability to use polymorphism. 
  * This method converts a runtime type to a type known at compile time allowing the correct polymorpyhic
  * implementation.
  * Typeclass could be used here as well but you'd lose the "exhaustiveness".
  */
  protected def dispatch(node: elm.Element, ctx: ContextStack): Any = {
    node match {
      case n: elm.After => visitAfter(n, ctx)
      case n: elm.AliasedQuerySource => visitAliasedQuerySource(n, ctx)
      case n: elm.And => visitAnd(n, ctx)
      case n: elm.Before => visitBefore(n, ctx)
      case n: elm.DateTime => visitDateTime(n, ctx)
      case n: elm.End => visitEnd(n, ctx)
      case n: elm.Equal => visitEqual(n, ctx)
      case n: elm.ExpressionDef => visitExpressionDef(n, ctx)
      case n: elm.Greater => visitGreater(n, ctx)
      case n: elm.In => visitIn(n, ctx)
      case n: elm.Less => visitLess(n, ctx)
      case n: elm.Library => visitLibrary(n, ctx)
      case n: elm.Literal => visitLiteral(n, ctx)
      case n: elm.NotEqual => visitNotEqual(n, ctx)
      case n: elm.ParameterRef => visitParameterRef(n, ctx)
      case n: elm.Property => visitProperty(n, ctx)
      case n: elm.Query => visitQuery(n, ctx)
      case n: elm.Retrieve => visitRetrieve(n, ctx)
      case n: elm.Start => visitStart(n, ctx)
      case n: elm.SingletonFrom => visitSingletonFrom(n, ctx)
      
      case n: elm.Null => null
      case null => null
      case n => {
        Log.warn(s"Translation of ELM type '${n.getClass().getTypeName()}' to Spark is not implemented.")
        null
      }
    }
  }

  protected def visitAfter(n: elm.After, ctx: ContextStack): Column = {
    visitBinaryExpression(n, ctx, _.gt(_))
  }

  protected def visitAliasedQuerySource(n: elm.AliasedQuerySource, ctx: ContextStack): QuerySourceResult = {
    val source = n.getExpression().eval(ctx).castTo[DataFrame]

    if (n.getAlias() != null) {
      QuerySourceResult(n.getResultTypeName, Some(n.getAlias()), source)
    } else {
      QuerySourceResult(n.getResultTypeName, None, source)
    }
  }

  protected def visitAnd(n: elm.And, ctx: ContextStack): Column = {
    visitBinaryExpression(n, ctx, _.and(_))
  }

  protected def visitBinaryExpression(n: elm.BinaryExpression, ctx: ContextStack, op: (Column, Column) => Column): Column = {
    assert(n.getOperand().size() == 2, s"Unexpected length of ${n.getOperand().size()} for binary expression ${n.getClass().getName()}")
    val leftOp = n.getOperand().get(0).eval(ctx).castTo[Column]
    val rightOp = n.getOperand().get(1).eval(ctx).castTo[Column]
    op(leftOp, rightOp)
  }

  protected def visitBefore(n: elm.Before, ctx: ContextStack): Column = {
    visitBinaryExpression(n, ctx, _.lt(_))
  }

  protected def visitCodeSystemRef(n: elm.CodeSystemRef, ctx: ContextStack): Unit = {
    ???
  }

  protected def visitDateTime(n: elm.DateTime, ctx: ContextStack): Column = {
    lit(n.convertTo[Timestamp])
  }

  protected def visitEnd(n: elm.End, ctx: ContextStack): Column = {
    val operand = n.getOperand().eval(ctx).castTo[ColumnResult]
    val (low, high) = modelAggregate.metaInterval(operand.path)
    operand.col.apply(high)
  }

  protected def visitEqual(n: elm.Equal, ctx: ContextStack): Column = {
    visitBinaryExpression(n, ctx, _.equalTo(_))
  }

  protected def visitExpressionDef(n: elm.ExpressionDef, ctx: ContextStack): ExpressionDefTranslation = {
    val result = n.getExpression().eval(ctx).castTo[DataFrame]
    ExpressionDefTranslation(n, result)
  }

  protected def visitGreater(n: elm.Greater, ctx: ContextStack): Column = {
    visitBinaryExpression(n, ctx, _.gt(_))
  }

  protected def visitIn(n: elm.In, ctx: ContextStack): Column = {
    visitBinaryExpression(n, ctx, (left, right) => left.between(right("low"), right("high")))
  }

  protected def visitLess(n: elm.Less, ctx: ContextStack): Column = {
    visitBinaryExpression(n, ctx, _.lt(_))
  }

  protected def visitLibrary(n: elm.Library, ctx: ContextStack): LibraryTranslation = {
    val errors = n.getAnnotation().asScala.filter(p => p.isInstanceOf[CqlToElmError])

    if (errors.length == 0) {
      val libraryExpressionDefs = n.getStatements().getDef().asScala.toSeq
      val statementEvals = libraryExpressionDefs.map(_.eval(ContextStack(ctx)).castTo[ExpressionDefTranslation])
      LibraryTranslation(n, statementEvals)
    } else {
      LibraryTranslation(n, Seq[ExpressionDefTranslation]())
    }
  }

  protected def visitLiteral(n: elm.Literal, ctx: ContextStack): Column = {
    val value = n.convertTo[Any]
    lit(value)
  }

  protected def visitNotEqual(n: elm.NotEqual, ctx: ContextStack): Column = {
    visitBinaryExpression(n, ctx, _.notEqual(_))
  }

  protected def visitParameterRef(n: elm.ParameterRef, ctx: ContextStack): Column = {
    val param = ctx.first[CallContext].parameter.filter(_._1 == n.getName())
    val paramValue = param(n.getName())
    
    paramValue match {
      case p: elm.Interval if p.getLow().isInstanceOf[elm.Date] && p.getHigh().isInstanceOf[elm.Date] => 
        val low = p.getLow().castTo[elm.Date].convertTo[LocalDate]
        val high = p.getLow().castTo[elm.Date].convertTo[LocalDate]
        struct(lit(low).alias("low"), lit(high).alias("high"))

      case p: elm.Interval if p.getLow().isInstanceOf[elm.DateTime] && p.getHigh().isInstanceOf[elm.DateTime] =>
        val low = p.getLow().castTo[elm.DateTime].convertTo[Timestamp]
        val high = p.getHigh().castTo[elm.DateTime].convertTo[Timestamp]
        struct(lit(low).alias("low"), lit(high).alias("high"))
    }
  }

  protected def visitProperty(n: elm.Property, ctx: ContextStack): ColumnResult = {
    val a = ctx.all[QuerySourceContext]
    val querySourceContext = ctx.all[QueryContext].flatMap(_.source.filter(f => f.name.get == n.getScope)).headOption
    val dataType = querySourceContext.getOrElse(throw new Exception(s"Unable to resolve query context")).dataType
    val column = col(s"${n.getScope()}.${n.getPath()}")
    ColumnResult(n.getPath(), dataType, n.getScope(), column)
  }

  protected def visitQuery(n: elm.Query, ctx: ContextStack): DataFrame = {
    val querySourceResult = n.getSource().asScala.map(a => {
      a.eval(ctx).castTo[QuerySourceResult]
    })

    val queryContext = QueryContext(
      querySourceResult.map(r => {
        QuerySourceContext(r.dataType, r.name, r.df)
      }).toList
    )
    val queryCtx = ctx.push(queryContext)

    val aggregateClause = n.getAggregate().eval(queryCtx)
    val letClause = n.getLet().asScala.map(_.eval(queryCtx))
    val relationshipClause = n.getRelationship().asScala.map(_.eval(queryCtx))
    val returnClause = n.getReturn().eval(queryCtx)
    val sortClause = n.getSort().eval(queryCtx)
    val whereClause = n.getWhere().eval(queryCtx).castTo[Column]

    queryCtx.last[QueryContext].source.head.df    // TODO: This should become a single DF after last clause
  }

  protected def visitRetrieve(n: elm.Retrieve, ctx: ContextStack): DataFrame = {
    val dataType = n.getDataType()
    sourceAggregate.acquireData(dataType).getOrElse(throw new Exception(s"Unable to retrieve ${dataType.toString()}"))
  }

  protected def visitStart(n: elm.Start, ctx: ContextStack): Column = {
    val operand = n.getOperand().eval(ctx).castTo[ColumnResult]
    val (low, high) = modelAggregate.metaInterval(operand.path)
    operand.col.apply(low)
  }

  protected def visitSingletonFrom(n: elm.SingletonFrom, ctx: ContextStack): DataFrame = {
    // TODO: Add support for filtering returned patients based on qualifying population or denominator
    n.getOperand.eval(ctx).castTo[DataFrame]
  }
}