package gov.va.sparkcql.translator.node

import org.hl7.elm.{r1 => elm}
import gov.va.sparkcql.io.Log
import org.cqframework.cql.elm.tracking.Trackable

object NodeFactory {

  def create(element: Object): Node = {
    element match {
      // case e: elm.After => visitAfter(n, ctx)
      // case e: elm.AliasedQuerySource => visitAliasedQuerySource(n, ctx)
      // case e: elm.And => visitAnd(n, ctx)
      // case e: elm.Before => visitBefore(n, ctx)
      case e: elm.Date => new DateNode(e)
      // case e: elm.DateTime => visitDateTime(n, ctx)
      // case e: elm.End => visitEnd(n, ctx)
      // case e: elm.Equal => visitEqual(n, ctx)
      case e: elm.ExpressionDef => new ExpressionDefNode(e)
      // case e: elm.Greater => visitGreater(n, ctx)
      // case e: elm.In => visitIn(n, ctx)
      // case e: elm.Less => visitLess(n, ctx)
      case e: elm.Library => new LibraryNode(e)
      case e: elm.List => new ListNode(e)
      case e: elm.Literal => new LiteralNode(e)
      // case e: elm.NotEqual => visitNotEqual(n, ctx)
      case e: elm.Null => new NullNode()
      // case e: elm.ParameterRef => visitParameterRef(n, ctx)
      // case e: elm.Property => visitProperty(n, ctx)
      // case e: elm.Query => visitQuery(n, ctx)
      // case e: elm.Retrieve => visitRetrieve(n, ctx)
      // case e: elm.Start => visitStart(n, ctx)
      // case e: elm.SingletonFrom => visitSingletonFrom(n, ctx)      
      case e: elm.Tuple => new TupleNode(e)
      case e: elm.TupleElement => new TupleElementNode(e)

      // case null => null
      case n => {
        Log.warn(s"Translation of ELM type '${element.getClass().getTypeName()}' to Spark is not implemented. Skipping node.")
        null
      }
    }
  }
}
