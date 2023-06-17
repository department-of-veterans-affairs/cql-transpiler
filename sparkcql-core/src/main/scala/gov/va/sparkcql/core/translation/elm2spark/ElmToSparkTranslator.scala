package gov.va.sparkcql.core.translation.elm2spark

import scala.reflect.runtime.universe._
import scala.collection.JavaConverters._
import org.apache.spark.sql.{SparkSession, Dataset, Row, Column}
import org.apache.spark.sql.functions._
import org.hl7.elm.r1._
import org.hl7.elm.r1.CodeSystemRef
import org.hl7.cql_annotations.r1._
import gov.va.sparkcql.core.Log
import gov.va.sparkcql.core.model.{Evaluation, LibraryEvaluation, StatementEvaluation}
import gov.va.sparkcql.core.conversion.Conversion
import gov.va.sparkcql.core.conversion.Conversion._
import gov.va.sparkcql.core.adapter.source.SourceAdapter
import gov.va.sparkcql.core.adapter.model.ModelAdapter
import gov.va.sparkcql.core.conversion.Convertable

abstract class ElmToSparkTranslator(sourceAdapters: Option[SourceAdapter], modelAdapters: Option[ModelAdapter], spark: SparkSession) {

  def translate(parameters: Option[Map[String, Object]], libraryCollection: Seq[Library]): Evaluation

  /**
  * Purpose is to provide eval dispatch and exhaustiveness checks for all ELM types. The type of each ELM
  * node is unknown at compile time and the ELM isn't inheritable which limits our ability to use polymorphism. 
  * This method converts a runtime type to a type known at compile time allowing the correct polymorpyhic
  * implementation.
  */
  protected def dispatch(node: Element, ctx: TranslationContext): Any

  /**
    * Syntactical sugar to add .eval() to any ELM Element node to improve readability.
    * Alias for eval(node)
    */
  implicit class EvalExtension(node: Element) {
    def eval(ctx: TranslationContext): Any = {
      node match {
        case null => Log.warn("Skipping null value detected during evaluation.")
        case _ => 
          Log.debug(s"Evaluating ${node.getClass().getName()}")
          dispatch(node, ctx)
      }
    }
  }

  /**
    * Syntactical sugar to add .to[] to any ELM Element node to improve readability.
    * Alias for node.asInstanceOf[]
    */
  implicit class ToExtension[T](node: T) {
    def to[T]: T = {
      node.asInstanceOf[T]
    }
  }

  /**
    * Syntactical sugar to add .into[] to any ELM Element node to improve readability.
    * Alias for convert[S, T](node)
    */
  implicit class IntoExtension[S : TypeTag](node: S) {
    def convert[T : TypeTag](implicit evidence: Convertable[S, T]): T = {
      Log.debug(s"${typeOf[S].typeSymbol.fullName} >> ${typeOf[T].typeSymbol.fullName}")
      Conversion.convert(node)
    }
  }
}