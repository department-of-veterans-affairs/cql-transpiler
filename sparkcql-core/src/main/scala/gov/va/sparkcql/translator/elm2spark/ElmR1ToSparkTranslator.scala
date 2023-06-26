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
import scala.collection.mutable.{Stack, ArrayStack, MutableList, HashMap}

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
      case n: elm.After => after(n, ctx)
      case n: elm.AliasedQuerySource => aliasedQuerySource(n, ctx)
      case n: elm.And => and(n, ctx)
      case n: elm.Before => before(n, ctx)
      case n: elm.DateTime => dateTime(n, ctx)
      case n: elm.End => end(n, ctx)
      case n: elm.Equal => equal(n, ctx)
      case n: elm.ExpressionDef => expressionDef(n, ctx)
      case n: elm.Greater => greater(n, ctx)
      case n: elm.In => in(n, ctx)
      case n: elm.Less => less(n, ctx)
      case n: elm.Library => library(n, ctx)
      case n: elm.Literal => literal(n, ctx)
      case n: elm.NotEqual => notEqual(n, ctx)
      case n: elm.ParameterRef => parameterRef(n, ctx)
      case n: elm.Property => property(n, ctx)
      case n: elm.Query => query(n, ctx)
      case n: elm.Retrieve => retrieve(n, ctx)
      case n: elm.Start => start(n, ctx)
      case n: elm.SingletonFrom => None
      
      case n: elm.Null => None
      case null => None
      case n => {
        Log.warn(s"Translation of ELM type '${n.getClass().getTypeName()}' to Spark is not implemented.")
        None
      }
    }
  }

  protected def after(n: elm.After, ctx: ContextStack): Column = {
    binaryExpression(n, ctx, _.gt(_))
  }

  protected def aliasedQuerySource(n: elm.AliasedQuerySource, ctx: ContextStack): Option[DataFrame] = {
    val source = n.getExpression().eval(ctx).castTo[Option[DataFrame]]

    if (n.getAlias() != null) {
      source.map(_.alias(n.getAlias()))
    } else {
      source
    }
  }

  protected def and(n: elm.And, ctx: ContextStack): Column = {
    binaryExpression(n, ctx, _.and(_))
  }

  protected def binaryExpression(n: elm.BinaryExpression, ctx: ContextStack, op: (Column, Column) => Column): Column = {
    assert(n.getOperand().size() == 2, s"Unexpected length of ${n.getOperand().size()} for binary expression ${n.getClass().getName()}")
    val leftOp = n.getOperand().get(0).eval(ctx).castTo[Column]
    val rightOp = n.getOperand().get(1).eval(ctx).castTo[Column]
    op(leftOp, rightOp)
  }

  protected def before(n: elm.Before, ctx: ContextStack): Column = {
    binaryExpression(n, ctx, _.lt(_))
  }

  protected def codeSystemRef(n: elm.CodeSystemRef, ctx: ContextStack): Unit = {
    ???
  }

  protected def dateTime(n: elm.DateTime, ctx: ContextStack): Column = {
    lit(n.convertTo[Timestamp])
  }

  protected def end(n: elm.End, ctx: ContextStack): Column = {
    val operand: Column = n.getOperand().eval(ctx).castTo[Column]
    val endProperty = "end"   // TODO
    operand.apply(endProperty)
  }

  protected def equal(n: elm.Equal, ctx: ContextStack): Column = {
    binaryExpression(n, ctx, _.equalTo(_))
  }

  protected def expressionDef(n: elm.ExpressionDef, ctx: ContextStack): ExpressionDefTranslation = {
    val result = n.getExpression().eval(ctx).castTo[Option[DataFrame]]
    ExpressionDefTranslation(n, result)
  }

  protected def greater(n: elm.Greater, ctx: ContextStack): Column = {
    binaryExpression(n, ctx, _.gt(_))
  }

  protected def in(n: elm.In, ctx: ContextStack): Column = {
    binaryExpression(n, ctx, (left, right) => left.between(right("low"), right("high")))
  }

  protected def less(n: elm.Less, ctx: ContextStack): Column = {
    binaryExpression(n, ctx, _.lt(_))
  }

  protected def library(n: elm.Library, ctx: ContextStack): LibraryTranslation = {
    val errors = n.getAnnotation().asScala.filter(p => p.isInstanceOf[CqlToElmError])

    if (errors.length == 0) {
      val libraryExpressionDefs = n.getStatements().getDef().asScala.toSeq
      val statementEvals = libraryExpressionDefs.map(_.eval(ContextStack(ctx)).castTo[ExpressionDefTranslation])
      LibraryTranslation(n, statementEvals)
    } else {
      LibraryTranslation(n, Seq[ExpressionDefTranslation]())
    }
  }

  protected def literal(n: elm.Literal, ctx: ContextStack): Column = {
    val value = n.convertTo[Any]
    lit(value)
  }

  protected def notEqual(n: elm.NotEqual, ctx: ContextStack): Column = {
    binaryExpression(n, ctx, _.notEqual(_))
  }

  protected def parameterRef(n: elm.ParameterRef, ctx: ContextStack): Column = {
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

  protected def property(n: elm.Property, ctx: ContextStack): Column = {
    col(s"${n.getScope()}.${n.getPath()}")
  }

  protected def query(n: elm.Query, ctx: ContextStack): Option[DataFrame] = {
    val querySource = n.getSource().asScala.map(a => {
      a.eval(ctx).castTo[Option[DataFrame]]
    })

    val querySourceContext = QueryContext(querySource.map(s => QuerySourceContext(null, "name", s.get)).toList)
    val queryCtx = ctx.push(querySourceContext)

    val aggregateClause = n.getAggregate().eval(queryCtx)
    val letClause = n.getLet().asScala.map(_.eval(queryCtx))
    val relationshipClause = n.getRelationship().asScala.map(_.eval(queryCtx))
    val returnClause = n.getReturn().eval(queryCtx)
    val sortClause = n.getSort().eval(queryCtx)
    val whereClause = n.getWhere().eval(queryCtx).castTo[Column]

    // queryCtx.contextDf.map(_.filter(whereClause))
    Some(queryCtx.last[QueryContext].source.head.df)
  }

  protected def retrieve(n: elm.Retrieve, ctx: ContextStack): Option[DataFrame] = {
    val dataType = n.getDataType()
    sourceAggregate.acquireData(dataType)
  }

  protected def start(n: elm.Start, ctx: ContextStack): Column = {
    val operand: Column = n.getOperand().eval(ctx).castTo[Column]
    val startProperty = "start"   // TODO
    operand.apply(startProperty)
  }
}