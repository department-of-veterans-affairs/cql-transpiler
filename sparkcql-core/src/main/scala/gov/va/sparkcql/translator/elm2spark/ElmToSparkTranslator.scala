package gov.va.sparkcql.translator.elm2spark

import scala.reflect.runtime.universe._
import scala.collection.JavaConverters._
import org.apache.spark.sql.{SparkSession, DataFrame, Column}
import org.apache.spark.sql.functions._
import org.hl7.elm.{r1 => elm}
import org.hl7.cql_annotations.r1._
import gov.va.sparkcql.logging.Log
import gov.va.sparkcql.converter.{Converter, Convertable}
import gov.va.sparkcql.converter.Converter._
import gov.va.sparkcql.model.{Model, ModelAggregator}
import gov.va.sparkcql.source.{Source, SourceAggregator}

/**
  * AST Translation Rules:
  * a. Child nodes should communicate to the Parent via return values and not side-effects 
  *    (e.g. children should not update shared mutable state).
  * b. Child nodes should remain unaware of their parents or their type to promote reuse and a clean design.
  * c. Parent nodes should expect a specific return type for an operand without awareness of the operand's 
  *    concrete type (e.g. And and Or both return a Col but a Where clause is unaware the type).
  * d. Related to (c), parents should not pass explicit parameters to child nodes. Instead, child nodes
  *    are expected to contain all the state they need to evaluate properly with the help of general context 
  *    information.
  *
  * @param models
  * @param sources
  * @param spark
  */
abstract class ElmToSparkTranslator(models: List[Model], sources: List[Source], spark: SparkSession) extends Translator {

  val modelAggregate = new ModelAggregator(models)
  val sourceAggregate = new SourceAggregator(sources)

  def translate(parameters: Map[String, Object], libraryCollection: List[elm.Library]): Translation

  /**
  * Purpose is to provide eval dispatch and exhaustiveness checks for all ELM types. The type of each ELM
  * node is unknown at compile time and the ELM isn't inheritable which limits our ability to use polymorphism. 
  * This method converts a runtime type to a type known at compile time allowing the correct polymorpyhic
  * implementation.
  */
  protected def dispatch(node: elm.Element, ctx: ContextStack): Any

  /**
    * Syntactical sugar to add .eval() to any ELM Element node to improve readability.
    * Alias for eval(node)
    */
  implicit class EvalExtension(node: elm.Element) {
    def eval(ctx: ContextStack): Any = {
      node match {
        case null => 
          Log.warn("Skipping null value detected during translation.")
          null
        case _ => 
          Log.debug(s"Evaluating ${node.getClass().getName()}")
          dispatch(node, ctx)
      }
    }
  }

  /**
    * Syntactical sugar to add .convertTo[] to any ELM Element node to improve readability.
    * Alias for Conversion.convert[S, T](node)
    */
  implicit class ConvertExtension[S](val s: S) {
    def convertTo[T](implicit evidence: Convertable[S, T]): T = {
      Converter.convert[S, T](s)
    }
  }

  /**
    * Syntactical sugar to add .castTo[] to any ELM Element node to improve readability.
    * Alias for node.asInstanceOf[]
    */
  implicit class CastToExtension[T >: Null](val node: T) {
    def castTo[T >: Null]: T = {
      node.asInstanceOf[T]
    }
  }
}