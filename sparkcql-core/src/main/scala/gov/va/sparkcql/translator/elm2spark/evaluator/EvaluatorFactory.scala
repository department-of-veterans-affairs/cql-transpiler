package gov.va.sparkcql.translator.elm2spark.evaluator

import org.hl7.elm.{r1 => elm}
import gov.va.sparkcql.logging.Log
import org.cqframework.cql.elm.tracking.Trackable

object EvaluatorFactory {

  def create(element: Object): Evaluator = {
    element match {
      // case e: elm.After => visitAfter(n, ctx)
      // case e: elm.AliasedQuerySource => visitAliasedQuerySource(n, ctx)
      // case e: elm.And => visitAnd(n, ctx)
      // case e: elm.Before => visitBefore(n, ctx)
      case e: elm.Date => new DateEvaluator(e)
      // case e: elm.DateTime => visitDateTime(n, ctx)
      // case e: elm.End => visitEnd(n, ctx)
      // case e: elm.Equal => visitEqual(n, ctx)
      case e: elm.ExpressionDef => new ExpressionDefEvaluator(e)
      // case e: elm.Greater => visitGreater(n, ctx)
      // case e: elm.In => visitIn(n, ctx)
      // case e: elm.Less => visitLess(n, ctx)
      case e: elm.Library => new LibraryEvaluator(e)
      case e: elm.List => new ListEvaluator(e)
      case e: elm.Literal => new LiteralEvaluator(e)
      // case e: elm.NotEqual => visitNotEqual(n, ctx)
      case e: elm.Null => new NullEvaluator()
      // case e: elm.ParameterRef => visitParameterRef(n, ctx)
      // case e: elm.Property => visitProperty(n, ctx)
      // case e: elm.Query => visitQuery(n, ctx)
      // case e: elm.Retrieve => visitRetrieve(n, ctx)
      // case e: elm.Start => visitStart(n, ctx)
      // case e: elm.SingletonFrom => visitSingletonFrom(n, ctx)      
      case e: elm.Tuple => new TupleEvaluator(e)
      case e: elm.TupleElement => new TupleElementEvaluator(e)

      // case null => null
      case n => {
        Log.warn(s"Translation of ELM type '${element.getClass().getTypeName()}' to Spark is not implemented. Skipping node.")
        null
      }
    }
  }
}
