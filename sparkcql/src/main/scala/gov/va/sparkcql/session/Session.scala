package gov.va.sparkcql.session

import org.apache.spark.sql.{SparkSession, Row, Dataset, DataFrame}
import scala.reflect.runtime.universe._
import gov.va.sparkcql.binding.{Binding, PredicateLike}
import gov.va.sparkcql.model.BoundType
import gov.va.sparkcql.binding.MockDataBinding
import scala.collection.mutable.HashMap
import gov.va.sparkcql.binding.BindingManager
import org.json4s._
import org.json4s.native.JsonMethods.{parse, compact}
import org.json4s.native.Serialization.write

final case class SessionConfiguration(bindings: Option[List[SessionConfigurationBindings]] = None, cqlCompilerUrl: Option[String])
final case class SessionConfigurationBindings(implementationClassName: String, isDefaultBinding: Option[Boolean], boundTypes: Option[List[String]], settings: Option[Map[String, Any]])

class Session private[Session](val spark: SparkSession, val configuration: SessionConfiguration) {

  protected val bindingManager = new BindingManager(this, configuration)

  def retrieve[T <: BoundType : TypeTag](): Option[Dataset[T]] = {
    bindingManager.retrieve[T](None)
  }

  def result[T](): Dataset[T] = {
    ???
  }

  def cql(elmText: String): Seq[DataFrame] = {
    ???
  }

  def elm(elmText: String): Seq[DataFrame] = {
    ???
  }
}

object Session {
  protected var activeSession: Option[Session] = None

  def apply(spark: SparkSession, sessionConfigurationJson: String): Session = {
    implicit val formats = DefaultFormats
    val configuration = parse(sessionConfigurationJson).extract[SessionConfiguration]
    apply(spark, configuration)
  }

  def apply(spark: SparkSession, sessionConfiguration: SessionConfiguration): Session = {
    activeSession.getOrElse {
      activeSession = Some(new Session(spark, sessionConfiguration))
      activeSession.get
    }
  }
}